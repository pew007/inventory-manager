package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import util.DBHelper;

public class Product {

    private String sku;
    private String category;
    private String vendor;
    private String productName;
    private Date   receivedDate;
    private Date   sentDate;
    private Date   lastModifiedDate;
    private int    quantityReceived;
    private int    quantitySent;

    public Product() {
        this.sku                = null;
        this.productName        = null;
        this.category           = null;
        this.vendor             = null;
        this.receivedDate       = new Date();
        this.sentDate           = new Date();
        this.lastModifiedDate   = new Date();
        this.quantityReceived   = 0;
        this.quantitySent       = 0;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getReceivedDateString() {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy/MM/dd");
        return dateFormatter.format(this.receivedDate);
    }

    public void setReceivedDate(String receivedDate) throws ParseException {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy");
        this.receivedDate = dateFormatter.parse(receivedDate);
    }

    public String getSentDateString() {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy/MM/dd");
        return dateFormatter.format(this.sentDate);
    }

    public void setSentDate(String sentDate) throws ParseException {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy");
        this.sentDate = dateFormatter.parse(sentDate);
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getLastModifiedDateString() {
        Date today = new Date();
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy/MM/dd");
        return dateFormatter.format(today);
    }

    public int getQuantityReceived() {
        Vector<String[]> result = DBHelper
                .doQuery("SELECT quantity " +
                        "FROM product, merchandise_in " +
                        "WHERE product.sku = '" + this.getSku() + "' " +
                        "AND merchandise_in.sku = product.sku");

        if (!result.isEmpty()) {
            return Integer.parseInt(result.elementAt(0)[0]);
        }

        return 0;
    }

    public void setQuantityReceived(int quantity) {
        this.quantityReceived = quantity;
    }

    public int getQuantitySent() {
        Vector<String[]> result = DBHelper
                .doQuery("SELECT quantity " +
                        "FROM product, merchandise_out " +
                        "WHERE product.sku = '" + this.getSku() + "' " +
                        "AND merchandise_out.sku = product.sku");

        if (!result.isEmpty()) {
            return Integer.parseInt(result.elementAt(0)[0]);
        }

        return 0;
    }

    public void setQuantitySent(int quantitySent) {
        this.quantitySent = quantitySent;
    }

    public static Product fetchProductFromDB(String sku) {
        Vector<String[]> result = DBHelper
                .doQuery("SELECT vendorModel, categoryName, vendorName " +
                        "FROM product, category, vendor " +
                        "WHERE product.sku = '" + sku + "' " +
                        "AND category.categoryID = product.categoryID " +
                        "AND vendor.vendorID = product.vendorID");

        if (!result.isEmpty()) {
            String productName = result.elementAt(0)[0];
            String category = result.elementAt(0)[1];
            String vendor = result.elementAt(0)[2];

            Product product = new Product();
            product.setSku(sku);
            product.setProductName(productName);
            product.setCategory(category);
            product.setVendor(vendor);

            return product;
        }

        return null;
    }

    public void receive() throws Exception {
        if (productReceived()) {
            updateReceivedProduct();
            updateProductOnHand();
        } else {
            addReceivedProduct();
            addProductOnHand();
        }
    }

    private boolean productReceived() throws Exception {
        Vector<String[]> result = DBHelper
                .doQuery("SELECT * FROM `merchandise_in` WHERE sku='" + this.getSku() + "'");
        return !result.isEmpty();
    }

    private void updateReceivedProduct() throws Exception {
        DBHelper.doUpdate("UPDATE `merchandise_in` " +
                "SET date='" + this.getReceivedDateString() + "', " +
                "quantity='" + this.quantityReceived + "' " +
                "WHERE sku='" + this.getSku() + "'");
    }

    private void addReceivedProduct() throws Exception {
        DBHelper.doUpdate("INSERT INTO `merchandise_in` VALUES ('"
                + this.getSku() + "', '"
                + this.getReceivedDateString() + "', '"
                + this.quantityReceived + "')");
    }

    private void addProductOnHand() {
        DBHelper.doUpdate("INSERT INTO `on_hand` VALUES ('"
                + this.getSku() + "', '"
                + this.getReceivedDateString() + "', '"
                + this.quantityReceived + "')");
    }

    private void updateProductOnHand() throws Exception {
        int quantity = this.getQuantityReceived() - this.getQuantitySent();
        DBHelper.doUpdate("UPDATE `on_hand` " +
                "SET last_modified='" + this.getLastModifiedDateString() + "', " +
                "quantity='" + quantity + "' " +
                "WHERE sku='" + this.getSku() + "'");
    }

    public void send() throws Exception {
        if (productOnHand()) {

            if (quantitySent > this.getQuantityReceived()) {
                throw new Exception("Do not have enough inventory on hand for " + this.getSku());
            }

            if (productSent()) {
                updateSentProduct();
            } else {
                addSentProduct();
            }
            updateProductOnHand();
        } else {
            throw new Exception("Product " + this.getSku() + " not on hand");
        }
    }

    private boolean productSent() throws Exception {
        Vector<String[]> result = DBHelper
                .doQuery("SELECT * FROM `merchandise_out` WHERE sku='" + this.getSku() + "'");
        return !result.isEmpty();
    }

    private void updateSentProduct() throws Exception {
        DBHelper.doUpdate("UPDATE `merchandise_out` " +
                "SET date='" + this.getSentDateString() + "', " +
                "quantity='" + this.quantitySent + "' " +
                "WHERE sku='" + this.getSku() + "'");
    }

    private void addSentProduct() throws Exception {
        DBHelper.doUpdate("INSERT INTO `merchandise_out` VALUES ('"
                + this.getSku() + "', '"
                + this.getReceivedDateString() + "', '"
                + this.quantitySent + "')");
    }

    private boolean productOnHand() {
        Vector<String[]> result = DBHelper
                .doQuery("SELECT * FROM `on_hand` WHERE sku='" + this.getSku() + "'");
        return !result.isEmpty();
    }
}
