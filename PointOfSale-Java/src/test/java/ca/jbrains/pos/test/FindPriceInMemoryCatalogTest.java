package ca.jbrains.pos.test;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class FindPriceInMemoryCatalogTest {
    @Test
    public void productFound() throws Exception {
        final Price matchingPrice = Price.euroCents(795);
        Catalog catalog = catalogWith("12345", matchingPrice);
        Assert.assertEquals(matchingPrice, catalog.findPrice("12345"));
    }

    @Test
    public void productNotFound() throws Exception {
        Catalog catalog = catalogWithout("12345");
        Assert.assertEquals(null, catalog.findPrice("12345"));
    }

    private Catalog catalogWith(final String barcode, Price matchingPrice) {
        return new InMemoryCatalog(new HashMap<String, Price>() {{
            put("not " + barcode, new Price());
            put("definitely not " + barcode, new Price());
            put(barcode, matchingPrice);
            put("still not " + barcode + ", you idiot", new Price());
        }});
    }

    private Catalog catalogWithout(String barcodeToAvoid) {
        return new InMemoryCatalog(new HashMap<String, Price>() {{
            put("not " + barcodeToAvoid, new Price());
            put("definitely not " + barcodeToAvoid, new Price());
            put("still not " + barcodeToAvoid + ", you idiot", new Price());
        }});
    }

    public static class InMemoryCatalog implements Catalog {
        private final Map<String, Price> pricesByBarcode;

        public InMemoryCatalog(Map<String, Price> pricesByBarcode) {
            this.pricesByBarcode = pricesByBarcode;
        }

        public Price findPrice(String barcode) {
            return pricesByBarcode.get(barcode);
        }
    }
}