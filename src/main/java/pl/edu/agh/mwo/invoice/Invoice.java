package pl.edu.agh.mwo.invoice;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import pl.edu.agh.mwo.invoice.product.Product;

public class Invoice {
    private Map<Product, Integer> products = new HashMap<Product, Integer>();
    private static int nextNumber = 0;
    private final int number = ++nextNumber;
    static int countQuantity = 0;
    public OtherProduct addProduct;

  
    public void addProduct(Product product) {
        addProduct(product, 1);
    }

    public void addProduct(Product product, Integer quantity) {
		if (product == null || quantity <= 0) {
			throw new IllegalArgumentException();
		}
		if (products.containsKey(product)) {
			Integer currentQuantity = this.products.get(product);
			this.products.put(product, currentQuantity + quantity);
		} else {
			this.products.put(product, quantity);
		}
	}

    public BigDecimal getNetTotal() {
        BigDecimal totalNet = BigDecimal.ZERO;
        for (Product product : products.keySet()) {
            BigDecimal quantity = new BigDecimal(products.get(product));
            totalNet = totalNet.add(product.getPrice().multiply(quantity));
        }
        return totalNet;
    }

    public BigDecimal getTaxTotal() {
        return getGrossTotal().subtract(getNetTotal());
    }

    public BigDecimal getGrossTotal() {
        BigDecimal totalGross = BigDecimal.ZERO;
        for (Product product : products.keySet()) {
            BigDecimal quantity = new BigDecimal(products.get(product));
            totalGross = totalGross.add(product.getPriceWithTax().multiply(quantity));
        }
        return totalGross;
    }

    public int getNumber() {
        return number;
       //return this.number;
    }

    public String getAsText() {
		StringBuilder sb = new StringBuilder("");
		sb.append("nr: " + this.number);
		DecimalFormat df = new DecimalFormat("0.00");
		
		for (Product product : products.keySet()) {
			BigDecimal quantity = new BigDecimal(products.get(product));
			sb.append(" " + product.getName() + " " + quantity + " " + df.format(product.getPrice()) + " ");											
			countQuantity++;			
		}
		
		sb.append("; Liczba pozycji: " + countQuantity);
				
		return  sb.toString();
	}
}
