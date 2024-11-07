package org.hanson.eddyshop.constants;

public class ErrorConstant {
    public static final String CATEGORY_NOT_FOUND = "Category Not Found";
    public static final String CATEGORY_ALREADY_EXIST = "Category Already Exist";
    public static final String PRODUCT_NOT_FOUND = "Product Not Found";
    public static final String IMAGE_NOT_FOUND = "Image Not Found";
    public static final String IMAGE_UPLOAD_FAILED = "Image Upload Failed";
    public static final String CLASS_CANNOT_BE_INSTANTIATED = "Class Can't Be Instantiated";
    public static final String IMAGE_FAILED_TO_LOAD = "Image Failed To Load";
    public static final String CART_NOT_FOUND = "Cart Not Found";
    public static final String CART_ITEM_NOT_FOUND = "Cart Item Not Found";
    public static final String ORDER_NOT_FOUND = "Order Not Found";
    public static final String ORDER_ITEM_NOT_FOUND = "Order Item Not Found";
    public static final String USER_NOT_FOUND = "User Not Found";
    public static final String ALREADY_EXISTS = "Already Exists";
    public static final String PRODUCT_ALREADY_EXISTS = "Product Already Exists, update Product instead";
    public static final String WRONG_CREDENTIALS = "Wrong Email or Password";


    private ErrorConstant() {
        throw new RuntimeException(ErrorConstant.CLASS_CANNOT_BE_INSTANTIATED);
    }
}
