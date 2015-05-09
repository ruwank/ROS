package com.relianceit.relianceorder.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;

import com.relianceit.relianceorder.models.ROSCustomer;
import com.relianceit.relianceorder.models.ROSNewOrder;
import com.relianceit.relianceorder.models.ROSNewOrderItem;
import com.relianceit.relianceorder.models.ROSProduct;
import com.relianceit.relianceorder.models.ROSReturnOrder;
import com.relianceit.relianceorder.models.ROSReturnOrderItem;
import com.relianceit.relianceorder.models.ROSStock;

import java.util.ArrayList;

/**
 * Created by sura on 4/28/15.
 */
public class ROSDbHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "ROS.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String INT_TYPE = " INT";
    private static final String DECIMAL_TYPE = " DECIMAL(10,5)";
    private static final String COMMA_SEP = ",";

    private boolean dbCreated = false;

    private static final String SQL_CREATE_CUSTOMER = "CREATE TABLE " + ROSDbConstants.Customer.TABLE_NAME +
            "(" +
            ROSDbConstants.Customer._ID + " INTEGER PRIMARY KEY," +
            ROSDbConstants.Customer.CL_NAME_CUSTOMER_ID + TEXT_TYPE + COMMA_SEP +
            ROSDbConstants.Customer.CL_NAME_FIRST_NAME + TEXT_TYPE + COMMA_SEP +
            ROSDbConstants.Customer.CL_NAME_LAST_NAME + TEXT_TYPE + COMMA_SEP +
            ROSDbConstants.Customer.CL_NAME_SHOP_NAME + TEXT_TYPE + COMMA_SEP +
            ROSDbConstants.Customer.CL_NAME_TELEPHONE + TEXT_TYPE + COMMA_SEP +
            ROSDbConstants.Customer.CL_NAME_EMAIL + TEXT_TYPE + COMMA_SEP +
            ROSDbConstants.Customer.CL_NAME_TOWN + TEXT_TYPE + COMMA_SEP +
            ROSDbConstants.Customer.CL_NAME_TOWN_ID + TEXT_TYPE + COMMA_SEP +
            ROSDbConstants.Customer.CL_NAME_ADDRESS1 + TEXT_TYPE + COMMA_SEP +
            ROSDbConstants.Customer.CL_NAME_ADDRESS2 + TEXT_TYPE + COMMA_SEP +
            ROSDbConstants.Customer.CL_NAME_ADDRESS3 + TEXT_TYPE + COMMA_SEP +
            ROSDbConstants.Customer.CL_NAME_OUTSTANDING + DECIMAL_TYPE + COMMA_SEP +
            ROSDbConstants.Customer.CL_NAME_CREDIT_LIMIT + DECIMAL_TYPE +
            ")";

    private static final String SQL_CREATE_NEW_ORDER = "CREATE TABLE " + ROSDbConstants.NewOrder.TABLE_NAME +
            "(" +
            ROSDbConstants.NewOrder._ID + " INTEGER PRIMARY KEY," +
            ROSDbConstants.NewOrder.CL_NAME_ORDER_ID + TEXT_TYPE + COMMA_SEP +
            ROSDbConstants.NewOrder.CL_NAME_ORDER_STATUS + INT_TYPE + COMMA_SEP +
            ROSDbConstants.NewOrder.CL_NAME_CUSTOMER_ID + TEXT_TYPE + COMMA_SEP +
            ROSDbConstants.NewOrder.CL_NAME_GROSS_VALUE + DECIMAL_TYPE + COMMA_SEP +
            ROSDbConstants.NewOrder.CL_NAME_DISCOUNT + DECIMAL_TYPE + COMMA_SEP +
            ROSDbConstants.NewOrder.CL_NAME_DISCOUNT_VALUE + DECIMAL_TYPE + COMMA_SEP +
            ROSDbConstants.NewOrder.CL_NAME_ORDER_VALUE + DECIMAL_TYPE + COMMA_SEP +
            ROSDbConstants.NewOrder.CL_NAME_ORDER_DATE + TEXT_TYPE +
            ")";

    private static final String SQL_CREATE_NEW_ORDER_ITEM = "CREATE TABLE " + ROSDbConstants.NewOrderItem.TABLE_NAME +
            "(" +
            ROSDbConstants.NewOrderItem._ID + " INTEGER PRIMARY KEY," +
            ROSDbConstants.NewOrderItem.CL_NAME_ITEM_ID + TEXT_TYPE + COMMA_SEP +
            ROSDbConstants.NewOrderItem.CL_NAME_ORDER_ID + TEXT_TYPE + COMMA_SEP +
            ROSDbConstants.NewOrderItem.CL_NAME_PRODUCT_ID + TEXT_TYPE + COMMA_SEP +
            ROSDbConstants.NewOrderItem.CL_NAME_PRODUCT_NAME + TEXT_TYPE + COMMA_SEP +
            ROSDbConstants.NewOrderItem.CL_NAME_BATCH_NAME + TEXT_TYPE + COMMA_SEP +
            ROSDbConstants.NewOrderItem.CL_NAME_QUANTITY + INT_TYPE + COMMA_SEP +
            ROSDbConstants.NewOrderItem.CL_NAME_PRICE + DECIMAL_TYPE + COMMA_SEP +
            ROSDbConstants.NewOrderItem.CL_NAME_DISCOUNT + DECIMAL_TYPE + COMMA_SEP +
            ROSDbConstants.NewOrderItem.CL_NAME_FREE_ISSUES + INT_TYPE + COMMA_SEP +
            ROSDbConstants.NewOrderItem.CL_NAME_ITEM_VALUE + DECIMAL_TYPE +
            ")";

    private static final String SQL_CREATE_RETURN_ORDER = "CREATE TABLE " + ROSDbConstants.ReturnOrder.TABLE_NAME +
            "(" +
            ROSDbConstants.ReturnOrder._ID + " INTEGER PRIMARY KEY," +
            ROSDbConstants.ReturnOrder.CL_NAME_ORDER_ID + TEXT_TYPE + COMMA_SEP +
            ROSDbConstants.ReturnOrder.CL_NAME_ORDER_STATUS + INT_TYPE + COMMA_SEP +
            ROSDbConstants.ReturnOrder.CL_NAME_CUSTOMER_ID + TEXT_TYPE + COMMA_SEP +
            ROSDbConstants.ReturnOrder.CL_NAME_GROSS_VALUE + DECIMAL_TYPE + COMMA_SEP +
            ROSDbConstants.ReturnOrder.CL_NAME_DISCOUNT + DECIMAL_TYPE + COMMA_SEP +
            ROSDbConstants.ReturnOrder.CL_NAME_DISCOUNT_VALUE + DECIMAL_TYPE + COMMA_SEP +
            ROSDbConstants.ReturnOrder.CL_NAME_ORDER_VALUE + DECIMAL_TYPE + COMMA_SEP +
            ROSDbConstants.ReturnOrder.CL_NAME_ORDER_DATE + TEXT_TYPE + COMMA_SEP +
            ROSDbConstants.ReturnOrder.CL_NAME_INVOICE_NO + TEXT_TYPE +
            ")";

    private static final String SQL_CREATE_RETURN_ORDER_ITEM = "CREATE TABLE " + ROSDbConstants.ReturnOrderItem.TABLE_NAME +
            "(" +
            ROSDbConstants.ReturnOrderItem._ID + " INTEGER PRIMARY KEY," +
            ROSDbConstants.ReturnOrderItem.CL_NAME_ITEM_ID + TEXT_TYPE + COMMA_SEP +
            ROSDbConstants.ReturnOrderItem.CL_NAME_ORDER_ID + TEXT_TYPE + COMMA_SEP +
            ROSDbConstants.ReturnOrderItem.CL_NAME_PRODUCT_ID + TEXT_TYPE + COMMA_SEP +
            ROSDbConstants.ReturnOrderItem.CL_NAME_PRODUCT_NAME + TEXT_TYPE + COMMA_SEP +
            ROSDbConstants.ReturnOrderItem.CL_NAME_BATCH_NAME + TEXT_TYPE + COMMA_SEP +
            ROSDbConstants.ReturnOrderItem.CL_NAME_QUANTITY + INT_TYPE + COMMA_SEP +
            ROSDbConstants.ReturnOrderItem.CL_NAME_PRICE + DECIMAL_TYPE + COMMA_SEP +
            ROSDbConstants.ReturnOrderItem.CL_NAME_DISCOUNT + DECIMAL_TYPE + COMMA_SEP +
            ROSDbConstants.ReturnOrderItem.CL_NAME_FREE_ISSUES + INT_TYPE + COMMA_SEP +
            ROSDbConstants.ReturnOrderItem.CL_NAME_ITEM_VALUE + DECIMAL_TYPE +
            ")";

    private static final String SQL_CREATE_STOCK = "CREATE TABLE " + ROSDbConstants.Stock.TABLE_NAME +
            "(" +
            ROSDbConstants.Stock._ID + " INTEGER PRIMARY KEY," +
            ROSDbConstants.Stock.CL_NAME_PRODUCT_NAME + TEXT_TYPE + COMMA_SEP +
            ROSDbConstants.Stock.CL_NAME_BATCH_NAME + TEXT_TYPE + COMMA_SEP +
            ROSDbConstants.Stock.CL_NAME_BRAND_NAME + TEXT_TYPE + COMMA_SEP +
            ROSDbConstants.Stock.CL_NAME_AGENCY_NAME + TEXT_TYPE + COMMA_SEP +
            ROSDbConstants.Stock.CL_NAME_ALLOCATED_QTY + INT_TYPE + COMMA_SEP +
            ROSDbConstants.Stock.CL_NAME_AVAILABLE_QTY + INT_TYPE + COMMA_SEP +
            ROSDbConstants.Stock.CL_NAME_STATUS + INT_TYPE + COMMA_SEP +
            ROSDbConstants.Stock.CL_NAME_AGENT_CODE + TEXT_TYPE + COMMA_SEP +
            ROSDbConstants.Stock.CL_NAME_BRAND_CODE + TEXT_TYPE + COMMA_SEP +
            ROSDbConstants.Stock.CL_NAME_PRODUCT_CODE + TEXT_TYPE + COMMA_SEP +
            ROSDbConstants.Stock.CL_NAME_COMP_CODE + TEXT_TYPE + COMMA_SEP +
            ROSDbConstants.Stock.CL_NAME_DISTRIB_CODE + TEXT_TYPE + COMMA_SEP +
            ROSDbConstants.Stock.CL_NAME_UNIT_PRICE + DECIMAL_TYPE +
            ")";

    private static final String SQL_CREATE_PRODUCT = "CREATE TABLE " + ROSDbConstants.Product.TABLE_NAME +
            "(" +
            ROSDbConstants.Product._ID + " INTEGER PRIMARY KEY," +
            ROSDbConstants.Product.CL_NAME_PRODUCT_NAME + TEXT_TYPE + COMMA_SEP +
            ROSDbConstants.Product.CL_NAME_BATCH_NAME + TEXT_TYPE + COMMA_SEP +
            ROSDbConstants.Product.CL_NAME_BRAND_NAME + TEXT_TYPE + COMMA_SEP +
            ROSDbConstants.Product.CL_NAME_AGENCY_NAME + TEXT_TYPE +
            ")";

    private static final String SQL_DELETE_CUSTOMER =
            "DROP TABLE IF EXISTS " + ROSDbConstants.Customer.TABLE_NAME;
    private static final String SQL_DELETE_NEW_ORDER =
            "DROP TABLE IF EXISTS " + ROSDbConstants.NewOrder.TABLE_NAME;
    private static final String SQL_DELETE_NEW_ORDER_ITEM =
            "DROP TABLE IF EXISTS " + ROSDbConstants.NewOrderItem.TABLE_NAME;
    private static final String SQL_DELETE_RETURN_ORDER =
            "DROP TABLE IF EXISTS " + ROSDbConstants.ReturnOrder.TABLE_NAME;
    private static final String SQL_DELETE_RETURN_ORDER_ITEM =
            "DROP TABLE IF EXISTS " + ROSDbConstants.ReturnOrderItem.TABLE_NAME;
    private static final String SQL_DELETE_STOCK =
            "DROP TABLE IF EXISTS " + ROSDbConstants.Stock.TABLE_NAME;
    private static final String SQL_DELETE_PRODUCT =
            "DROP TABLE IF EXISTS " + ROSDbConstants.Product.TABLE_NAME;

    public ROSDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_CUSTOMER);
        db.execSQL(SQL_CREATE_NEW_ORDER);
        db.execSQL(SQL_CREATE_NEW_ORDER_ITEM);
        db.execSQL(SQL_CREATE_RETURN_ORDER);
        db.execSQL(SQL_CREATE_RETURN_ORDER_ITEM);
        db.execSQL(SQL_CREATE_STOCK);
        db.execSQL(SQL_CREATE_PRODUCT);

        dbCreated = true;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_NEW_ORDER_ITEM);
        db.execSQL(SQL_DELETE_RETURN_ORDER_ITEM);
        db.execSQL(SQL_DELETE_NEW_ORDER);
        db.execSQL(SQL_DELETE_RETURN_ORDER);
        db.execSQL(SQL_DELETE_STOCK);
        db.execSQL(SQL_DELETE_CUSTOMER);
        db.execSQL(SQL_DELETE_PRODUCT);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public SQLiteDatabase getReadableDb(Context context) {
        ROSDbHelper dbHelper = new ROSDbHelper(context);
        return dbHelper.getReadableDatabase();
    }

    public SQLiteDatabase getWritableDb(Context context) {
        ROSDbHelper dbHelper = new ROSDbHelper(context);
        return dbHelper.getWritableDatabase();
    }

    /*
    Customer section
     */
    public ArrayList<ROSCustomer> getCustomers(Context context) {
        SQLiteDatabase db = getReadableDb(context);

        final String SQL_SELECT_ALL_CUSTOMERS = "SELECT * FROM " + ROSDbConstants.Customer.TABLE_NAME +
                " ORDER BY " + ROSDbConstants.Customer.CL_NAME_FIRST_NAME + ";";
        Cursor c = db.rawQuery(SQL_SELECT_ALL_CUSTOMERS, null);

        ArrayList<ROSCustomer> cusList = new ArrayList<ROSCustomer>();

        if (c != null) {
            while (c.moveToNext()){
                ROSCustomer cus = new ROSCustomer();
                cus.setAddress1(c.getString(c.getColumnIndexOrThrow(ROSDbConstants.Customer.CL_NAME_ADDRESS1)));
                cus.setAddress2(c.getString(c.getColumnIndexOrThrow(ROSDbConstants.Customer.CL_NAME_ADDRESS2)));
                cus.setAddress3(c.getString(c.getColumnIndexOrThrow(ROSDbConstants.Customer.CL_NAME_ADDRESS2)));
                cus.setCustomerId(c.getString(c.getColumnIndexOrThrow(ROSDbConstants.Customer.CL_NAME_CUSTOMER_ID)));
                cus.setEmail(c.getString(c.getColumnIndexOrThrow(ROSDbConstants.Customer.CL_NAME_EMAIL)));
                cus.setFirstName(c.getString(c.getColumnIndexOrThrow(ROSDbConstants.Customer.CL_NAME_FIRST_NAME)));
                cus.setLastName(c.getString(c.getColumnIndexOrThrow(ROSDbConstants.Customer.CL_NAME_LAST_NAME)));
                cus.setShopName(c.getString(c.getColumnIndexOrThrow(ROSDbConstants.Customer.CL_NAME_SHOP_NAME)));
                cus.setTelephone(c.getString(c.getColumnIndexOrThrow(ROSDbConstants.Customer.CL_NAME_TELEPHONE)));
                cus.setTown(c.getString(c.getColumnIndexOrThrow(ROSDbConstants.Customer.CL_NAME_TOWN)));
                cus.setTownId(c.getString(c.getColumnIndexOrThrow(ROSDbConstants.Customer.CL_NAME_TOWN_ID)));
                cus.setOutstanding(c.getDouble(c.getColumnIndexOrThrow(ROSDbConstants.Customer.CL_NAME_OUTSTANDING)));
                cus.setCreditLimit(c.getDouble(c.getColumnIndexOrThrow(ROSDbConstants.Customer.CL_NAME_CREDIT_LIMIT)));

                cusList.add(cus);
            }

            c.close();
            db.close();
        }

        return  cusList;
    }

    public void updateCustomerOutstanding(Context context, String customerId, double value) {
        SQLiteDatabase db = getWritableDb(context);

        final String SQL_UPDATE_CUSTOMER = "UPDATE " + ROSDbConstants.Customer.TABLE_NAME +
                " SET " + ROSDbConstants.Customer.CL_NAME_OUTSTANDING + " = " + value +
                " WHERE " + ROSDbConstants.Customer.CL_NAME_CUSTOMER_ID + " = '" + customerId + "';";
        db.execSQL(SQL_UPDATE_CUSTOMER);
        db.close();
    }

    public void clearCustomerTable(Context context) {
        SQLiteDatabase db = getWritableDb(context);

        final String SQL_DELETE_CUSTOMER = "DELETE FROM " + ROSDbConstants.Customer.TABLE_NAME + ";";
        db.execSQL(SQL_DELETE_CUSTOMER);
        db.close();
    }

    public void insertCustomer(Context context, ROSCustomer customer) {
        SQLiteDatabase db = getWritableDb(context);

        ContentValues values = new ContentValues();
        values.put(ROSDbConstants.Customer.CL_NAME_CUSTOMER_ID, customer.getCustomerId());
        values.put(ROSDbConstants.Customer.CL_NAME_FIRST_NAME, customer.getFirstName());
        values.put(ROSDbConstants.Customer.CL_NAME_LAST_NAME, customer.getLastName());
        values.put(ROSDbConstants.Customer.CL_NAME_EMAIL, customer.getEmail());
        values.put(ROSDbConstants.Customer.CL_NAME_TELEPHONE, customer.getTelephone());
        values.put(ROSDbConstants.Customer.CL_NAME_SHOP_NAME, customer.getShopName());
        values.put(ROSDbConstants.Customer.CL_NAME_TOWN, customer.getTown());
        values.put(ROSDbConstants.Customer.CL_NAME_TOWN_ID, customer.getTownId());
        values.put(ROSDbConstants.Customer.CL_NAME_ADDRESS1, customer.getAddress1());
        values.put(ROSDbConstants.Customer.CL_NAME_ADDRESS2, customer.getAddress2());
        values.put(ROSDbConstants.Customer.CL_NAME_ADDRESS3, customer.getAddress3());
        values.put(ROSDbConstants.Customer.CL_NAME_OUTSTANDING, customer.getOutstanding());
        values.put(ROSDbConstants.Customer.CL_NAME_CREDIT_LIMIT, customer.getCreditLimit());

        db.insert(ROSDbConstants.Customer.TABLE_NAME, null, values);
        db.close();
    }

    public void insertCustomers(Context context, ArrayList<ROSCustomer>customers) {
        SQLiteDatabase db = getWritableDb(context);

        for (int i = 0; i < customers.size(); i++) {
            ROSCustomer customer = customers.get(i);

            ContentValues values = new ContentValues();
            values.put(ROSDbConstants.Customer.CL_NAME_CUSTOMER_ID, customer.getCustomerId());
            values.put(ROSDbConstants.Customer.CL_NAME_FIRST_NAME, customer.getFirstName());
            values.put(ROSDbConstants.Customer.CL_NAME_LAST_NAME, customer.getLastName());
            values.put(ROSDbConstants.Customer.CL_NAME_EMAIL, customer.getEmail());
            values.put(ROSDbConstants.Customer.CL_NAME_TELEPHONE, customer.getTelephone());
            values.put(ROSDbConstants.Customer.CL_NAME_SHOP_NAME, customer.getShopName());
            values.put(ROSDbConstants.Customer.CL_NAME_TOWN, customer.getTown());
            values.put(ROSDbConstants.Customer.CL_NAME_TOWN_ID, customer.getTownId());
            values.put(ROSDbConstants.Customer.CL_NAME_ADDRESS1, customer.getAddress1());
            values.put(ROSDbConstants.Customer.CL_NAME_ADDRESS2, customer.getAddress2());
            values.put(ROSDbConstants.Customer.CL_NAME_ADDRESS3, customer.getAddress3());
            values.put(ROSDbConstants.Customer.CL_NAME_OUTSTANDING, customer.getOutstanding());
            values.put(ROSDbConstants.Customer.CL_NAME_CREDIT_LIMIT, customer.getCreditLimit());

            db.insert(ROSDbConstants.Customer.TABLE_NAME, null, values);
        }

        db.close();
    }

    /*
    New Order section
     */
    public ArrayList<ROSNewOrder> getNewOrders(Context context, String customerId) {
        SQLiteDatabase db = getReadableDb(context);

        final String SQL_SELECT_NEW_ORDERS = "SELECT * FROM " + ROSDbConstants.NewOrder.TABLE_NAME +
                " WHERE " + ROSDbConstants.NewOrder.CL_NAME_CUSTOMER_ID + " = '" + customerId + "';";
        Cursor c = db.rawQuery(SQL_SELECT_NEW_ORDERS, null);

        ArrayList<ROSNewOrder> orderList = new ArrayList<ROSNewOrder>();

        if (c != null) {
            while (c.moveToNext()){
                ROSNewOrder order = new ROSNewOrder();
                order.setCustomerId(customerId);
                order.setOrderId(c.getString(c.getColumnIndexOrThrow(ROSDbConstants.NewOrder.CL_NAME_ORDER_ID)));
                order.setOrderDate(c.getString(c.getColumnIndexOrThrow(ROSDbConstants.NewOrder.CL_NAME_ORDER_DATE)));
                order.setDiscount(c.getDouble(c.getColumnIndexOrThrow(ROSDbConstants.NewOrder.CL_NAME_DISCOUNT)));
                order.setDiscountValue(c.getDouble(c.getColumnIndexOrThrow(ROSDbConstants.NewOrder.CL_NAME_DISCOUNT_VALUE)));
                order.setGrossValue(c.getDouble(c.getColumnIndexOrThrow(ROSDbConstants.NewOrder.CL_NAME_GROSS_VALUE)));
                order.setOrderValue(c.getDouble(c.getColumnIndexOrThrow(ROSDbConstants.NewOrder.CL_NAME_ORDER_VALUE)));
                order.setOrderStatus(c.getInt(c.getColumnIndexOrThrow(ROSDbConstants.NewOrder.CL_NAME_ORDER_STATUS)));
                orderList.add(order);
            }

            c.close();
            db.close();
        }

        return  orderList;
    }

    public ArrayList<ROSNewOrder> getNewOrders(Context context, int orderStatus) {
        SQLiteDatabase db = getReadableDb(context);

        final String SQL_SELECT_NEW_ORDERS = "SELECT * FROM " + ROSDbConstants.NewOrder.TABLE_NAME +
                " WHERE " + ROSDbConstants.NewOrder.CL_NAME_ORDER_STATUS + " = " + orderStatus + ";";
        Cursor c = db.rawQuery(SQL_SELECT_NEW_ORDERS, null);

        ArrayList<ROSNewOrder> orderList = new ArrayList<ROSNewOrder>();

        if (c != null) {
            while (c.moveToNext()){
                ROSNewOrder order = new ROSNewOrder();
                order.setCustomerId(c.getString(c.getColumnIndexOrThrow(ROSDbConstants.NewOrder.CL_NAME_CUSTOMER_ID)));
                order.setOrderId(c.getString(c.getColumnIndexOrThrow(ROSDbConstants.NewOrder.CL_NAME_ORDER_ID)));
                order.setOrderDate(c.getString(c.getColumnIndexOrThrow(ROSDbConstants.NewOrder.CL_NAME_ORDER_DATE)));
                order.setDiscount(c.getDouble(c.getColumnIndexOrThrow(ROSDbConstants.NewOrder.CL_NAME_DISCOUNT)));
                order.setDiscountValue(c.getDouble(c.getColumnIndexOrThrow(ROSDbConstants.NewOrder.CL_NAME_DISCOUNT_VALUE)));
                order.setGrossValue(c.getDouble(c.getColumnIndexOrThrow(ROSDbConstants.NewOrder.CL_NAME_GROSS_VALUE)));
                order.setOrderValue(c.getDouble(c.getColumnIndexOrThrow(ROSDbConstants.NewOrder.CL_NAME_ORDER_VALUE)));
                order.setOrderStatus(c.getInt(c.getColumnIndexOrThrow(ROSDbConstants.NewOrder.CL_NAME_ORDER_STATUS)));
                orderList.add(order);
            }

            c.close();
            db.close();
        }

        for (int i = 0; i < orderList.size(); i++) {
            ROSNewOrder order = orderList.get(i);
            order.setOrderItems(getNewOrderItems(context, order.getOrderId()));
        }

        return  orderList;
    }

    public ROSNewOrder getNewOrder(Context context, String orderId) {
        SQLiteDatabase db = getReadableDb(context);

        final String SQL_SELECT_NEW_ORDER = "SELECT * FROM " + ROSDbConstants.NewOrder.TABLE_NAME +
                " WHERE " + ROSDbConstants.NewOrder.CL_NAME_ORDER_ID + " = '" + orderId + "';";
        Cursor c = db.rawQuery(SQL_SELECT_NEW_ORDER, null);

        ROSNewOrder order = null;

        if (c != null) {

            c.moveToFirst();
            order = new ROSNewOrder();
            order.setCustomerId(c.getString(c.getColumnIndexOrThrow(ROSDbConstants.NewOrder.CL_NAME_CUSTOMER_ID)));
            order.setOrderId(c.getString(c.getColumnIndexOrThrow(ROSDbConstants.NewOrder.CL_NAME_ORDER_ID)));
            order.setOrderDate(c.getString(c.getColumnIndexOrThrow(ROSDbConstants.NewOrder.CL_NAME_ORDER_DATE)));
            order.setDiscount(c.getDouble(c.getColumnIndexOrThrow(ROSDbConstants.NewOrder.CL_NAME_DISCOUNT)));
            order.setDiscountValue(c.getDouble(c.getColumnIndexOrThrow(ROSDbConstants.NewOrder.CL_NAME_DISCOUNT_VALUE)));
            order.setGrossValue(c.getDouble(c.getColumnIndexOrThrow(ROSDbConstants.NewOrder.CL_NAME_GROSS_VALUE)));
            order.setOrderValue(c.getDouble(c.getColumnIndexOrThrow(ROSDbConstants.NewOrder.CL_NAME_ORDER_VALUE)));
            order.setOrderStatus(c.getInt(c.getColumnIndexOrThrow(ROSDbConstants.NewOrder.CL_NAME_ORDER_STATUS)));

            c.close();
            db.close();
        }

        if (order != null) {
            order.setOrderItems(getNewOrderItems(context, orderId));
        }

        return order;
    }

    public void updateNewOrderStatus(Context context, String orderId, int orderStatus) {
        SQLiteDatabase db = getWritableDb(context);

        final String SQL_UPDATE_ORDER_STATUS = "UPDATE " + ROSDbConstants.NewOrder.TABLE_NAME +
                " SET " + ROSDbConstants.NewOrder.CL_NAME_ORDER_STATUS + " = " + orderStatus +
                " WHERE " + ROSDbConstants.NewOrder.CL_NAME_ORDER_ID + " = '" + orderId + "';";
        db.execSQL(SQL_UPDATE_ORDER_STATUS);
        db.close();
    }

    public String insertNewOrder(Context context, ROSNewOrder order) {
        SQLiteDatabase db = getWritableDb(context);

        ContentValues values = new ContentValues();
        values.put(ROSDbConstants.NewOrder.CL_NAME_CUSTOMER_ID, order.getCustomerId());
        values.put(ROSDbConstants.NewOrder.CL_NAME_ORDER_DATE, order.getOrderDate());
        values.put(ROSDbConstants.NewOrder.CL_NAME_DISCOUNT, order.getDiscount());
        values.put(ROSDbConstants.NewOrder.CL_NAME_DISCOUNT_VALUE, order.getDiscountValue());
        values.put(ROSDbConstants.NewOrder.CL_NAME_GROSS_VALUE, order.getGrossValue());
        values.put(ROSDbConstants.NewOrder.CL_NAME_ORDER_VALUE, order.getOrderValue());
        values.put(ROSDbConstants.NewOrder.CL_NAME_ORDER_STATUS, order.getOrderStatus());

        long orderId = db.insert(ROSDbConstants.NewOrder.TABLE_NAME, null, values);
        db.close();

        if (orderId == -1) {
            return null;
        }

        db = getWritableDb(context);
        String orderIdStr = "" + orderId;
        final String SQL_UPDATE_NEW_ORDER = "UPDATE " + ROSDbConstants.NewOrder.TABLE_NAME +
                " SET " + ROSDbConstants.NewOrder.CL_NAME_ORDER_ID + " = '" + orderIdStr +
                "' WHERE " + ROSDbConstants.NewOrder._ID + " = " + orderId + ";";
        db.execSQL(SQL_UPDATE_NEW_ORDER);
        db.close();

        //inserting order items
        boolean success = insertNewOrderItems(context, orderIdStr, order.getOrderItems());
        if (!success) {
            db = getWritableDb(context);
            final String SQL_DELETE_NEW_ORDER = "DELETE FROM " + ROSDbConstants.NewOrder.TABLE_NAME +
                    " WHERE " + ROSDbConstants.NewOrder._ID + " = " + orderId + ";";
            db.execSQL(SQL_DELETE_NEW_ORDER);
            db.close();

            return null;
        }

        return orderIdStr;
    }

    public void clearNewOrderTable(Context context) {
        SQLiteDatabase db = getWritableDb(context);

        final String SQL_DELETE_NEW_ORDER = "DELETE FROM " + ROSDbConstants.NewOrder.TABLE_NAME + ";";
        db.execSQL(SQL_DELETE_NEW_ORDER);
        db.close();
    }

    /*
    New Order Item section
     */
    public ArrayList<ROSNewOrderItem> getNewOrderItems(Context context, String orderId) {
        SQLiteDatabase db = getReadableDb(context);

        final String SQL_SELECT_NEW_ORDER_ITEMS = "SELECT * FROM " + ROSDbConstants.NewOrderItem.TABLE_NAME +
                " WHERE " + ROSDbConstants.NewOrderItem.CL_NAME_ORDER_ID + " = '" + orderId + "';";
        Cursor c = db.rawQuery(SQL_SELECT_NEW_ORDER_ITEMS, null);

        ArrayList<ROSNewOrderItem> orderItemList = new ArrayList<ROSNewOrderItem>();
        if (c != null) {
            while (c.moveToNext()){
                ROSNewOrderItem orderItem = new ROSNewOrderItem();
                orderItem.setItemId(c.getString(c.getColumnIndexOrThrow(ROSDbConstants.NewOrderItem.CL_NAME_ITEM_ID)));
                orderItem.setOrderId(c.getString(c.getColumnIndexOrThrow(ROSDbConstants.NewOrderItem.CL_NAME_ORDER_ID)));
                orderItem.setProductId(c.getString(c.getColumnIndexOrThrow(ROSDbConstants.NewOrderItem.CL_NAME_PRODUCT_ID)));
                orderItem.setProductName(c.getString(c.getColumnIndexOrThrow(ROSDbConstants.NewOrderItem.CL_NAME_PRODUCT_NAME)));
                orderItem.setBatchName(c.getString(c.getColumnIndexOrThrow(ROSDbConstants.NewOrderItem.CL_NAME_BATCH_NAME)));
                orderItem.setPrice(c.getDouble(c.getColumnIndexOrThrow(ROSDbConstants.NewOrderItem.CL_NAME_PRICE)));
                orderItem.setDiscount(c.getDouble(c.getColumnIndexOrThrow(ROSDbConstants.NewOrderItem.CL_NAME_DISCOUNT)));
                orderItem.setItemValue(c.getDouble(c.getColumnIndexOrThrow(ROSDbConstants.NewOrderItem.CL_NAME_ITEM_VALUE)));
                orderItem.setQuantity(c.getInt(c.getColumnIndexOrThrow(ROSDbConstants.NewOrderItem.CL_NAME_QUANTITY)));
                orderItem.setFreeIssues(c.getInt(c.getColumnIndexOrThrow(ROSDbConstants.NewOrderItem.CL_NAME_FREE_ISSUES)));
                orderItemList.add(orderItem);
            }

            c.close();
            db.close();
        }

        return orderItemList;
    }

    protected boolean insertNewOrderItems(Context context, String orderId, ArrayList<ROSNewOrderItem> items) {

        SQLiteDatabase db = getWritableDb(context);
        boolean success = true;

        for (int i = 0; i < items.size(); i++) {

            ROSNewOrderItem orderItem = items.get(i);

            ContentValues values = new ContentValues();
            values.put(ROSDbConstants.NewOrderItem.CL_NAME_ORDER_ID, orderId);
            values.put(ROSDbConstants.NewOrderItem.CL_NAME_PRODUCT_ID, orderItem.getProductId());
            values.put(ROSDbConstants.NewOrderItem.CL_NAME_PRODUCT_NAME, orderItem.getProductName());
            values.put(ROSDbConstants.NewOrderItem.CL_NAME_BATCH_NAME, orderItem.getBatchName());
            values.put(ROSDbConstants.NewOrderItem.CL_NAME_PRICE, orderItem.getPrice());
            values.put(ROSDbConstants.NewOrderItem.CL_NAME_DISCOUNT, orderItem.getDiscount());
            values.put(ROSDbConstants.NewOrderItem.CL_NAME_ITEM_VALUE, orderItem.getItemValue());
            values.put(ROSDbConstants.NewOrderItem.CL_NAME_QUANTITY, orderItem.getQuantity());
            values.put(ROSDbConstants.NewOrderItem.CL_NAME_FREE_ISSUES, orderItem.getFreeIssues());

            long itemId = db.insert(ROSDbConstants.NewOrderItem.TABLE_NAME, null, values);
            if (itemId == -1) {
                success = false;
                break;
            }
        }
        db.close();

        if (!success) {
            deleteNewOrderItems(context, orderId);
        }

        return success;
    }

    protected void deleteNewOrderItems(Context context, String orderId) {
        SQLiteDatabase db = getWritableDb(context);

        final String SQL_DELETE_NEW_ORDER_ITEMS = "DELETE FROM " + ROSDbConstants.NewOrderItem.TABLE_NAME
                + " WHERE " + ROSDbConstants.NewOrderItem.CL_NAME_ORDER_ID + " = '" + orderId + "';";
        db.execSQL(SQL_DELETE_NEW_ORDER_ITEMS);
        db.close();
    }

    public void clearNewOrderItemTable(Context context) {
        SQLiteDatabase db = getWritableDb(context);

        final String SQL_DELETE_NEW_ORDER_ITEMS = "DELETE FROM " + ROSDbConstants.NewOrderItem.TABLE_NAME + ";";
        db.execSQL(SQL_DELETE_NEW_ORDER_ITEMS);
        db.close();
    }

    /*
    Return Order section
     */
    public ArrayList<ROSReturnOrder> getReturnOrders(Context context, String customerId) {
        SQLiteDatabase db = getReadableDb(context);

        final String SQL_SELECT_RETURN_ORDERS = "SELECT * FROM " + ROSDbConstants.ReturnOrder.TABLE_NAME +
                " WHERE " + ROSDbConstants.ReturnOrder.CL_NAME_CUSTOMER_ID + " = '" + customerId + "';";
        Cursor c = db.rawQuery(SQL_SELECT_RETURN_ORDERS, null);

        ArrayList<ROSReturnOrder> orderList = new ArrayList<ROSReturnOrder>();

        if (c != null) {
            while (c.moveToNext()){
                ROSReturnOrder order = new ROSReturnOrder();
                order.setCustomerId(c.getString(c.getColumnIndexOrThrow(ROSDbConstants.ReturnOrder.CL_NAME_CUSTOMER_ID)));
                order.setOrderId(c.getString(c.getColumnIndexOrThrow(ROSDbConstants.ReturnOrder.CL_NAME_ORDER_ID)));
                order.setOrderDate(c.getString(c.getColumnIndexOrThrow(ROSDbConstants.ReturnOrder.CL_NAME_ORDER_DATE)));
                order.setInvoiceNo(c.getString(c.getColumnIndexOrThrow(ROSDbConstants.ReturnOrder.CL_NAME_INVOICE_NO)));
                order.setDiscount(c.getDouble(c.getColumnIndexOrThrow(ROSDbConstants.ReturnOrder.CL_NAME_DISCOUNT)));
                order.setDiscountValue(c.getDouble(c.getColumnIndexOrThrow(ROSDbConstants.ReturnOrder.CL_NAME_DISCOUNT_VALUE)));
                order.setGrossValue(c.getDouble(c.getColumnIndexOrThrow(ROSDbConstants.ReturnOrder.CL_NAME_GROSS_VALUE)));
                order.setOrderValue(c.getDouble(c.getColumnIndexOrThrow(ROSDbConstants.ReturnOrder.CL_NAME_ORDER_VALUE)));
                order.setOrderStatus(c.getInt(c.getColumnIndexOrThrow(ROSDbConstants.ReturnOrder.CL_NAME_ORDER_STATUS)));
                orderList.add(order);
            }

            c.close();
            db.close();
        }

        return  orderList;
    }

    public ArrayList<ROSReturnOrder> getReturnOrders(Context context, int orderStatus) {
        SQLiteDatabase db = getReadableDb(context);

        final String SQL_SELECT_RETURN_ORDERS = "SELECT * FROM " + ROSDbConstants.ReturnOrder.TABLE_NAME +
                " WHERE " + ROSDbConstants.ReturnOrder.CL_NAME_ORDER_STATUS + " = " + orderStatus + ";";
        Cursor c = db.rawQuery(SQL_SELECT_RETURN_ORDERS, null);

        ArrayList<ROSReturnOrder> orderList = new ArrayList<ROSReturnOrder>();

        if (c != null) {
            while (c.moveToNext()){
                ROSReturnOrder order = new ROSReturnOrder();
                order.setCustomerId(c.getString(c.getColumnIndexOrThrow(ROSDbConstants.ReturnOrder.CL_NAME_CUSTOMER_ID)));
                order.setOrderId(c.getString(c.getColumnIndexOrThrow(ROSDbConstants.ReturnOrder.CL_NAME_ORDER_ID)));
                order.setOrderDate(c.getString(c.getColumnIndexOrThrow(ROSDbConstants.ReturnOrder.CL_NAME_ORDER_DATE)));
                order.setInvoiceNo(c.getString(c.getColumnIndexOrThrow(ROSDbConstants.ReturnOrder.CL_NAME_INVOICE_NO)));
                order.setDiscount(c.getDouble(c.getColumnIndexOrThrow(ROSDbConstants.ReturnOrder.CL_NAME_DISCOUNT)));
                order.setDiscountValue(c.getDouble(c.getColumnIndexOrThrow(ROSDbConstants.ReturnOrder.CL_NAME_DISCOUNT_VALUE)));
                order.setGrossValue(c.getDouble(c.getColumnIndexOrThrow(ROSDbConstants.ReturnOrder.CL_NAME_GROSS_VALUE)));
                order.setOrderValue(c.getDouble(c.getColumnIndexOrThrow(ROSDbConstants.ReturnOrder.CL_NAME_ORDER_VALUE)));
                order.setOrderStatus(c.getInt(c.getColumnIndexOrThrow(ROSDbConstants.ReturnOrder.CL_NAME_ORDER_STATUS)));
                orderList.add(order);
            }

            c.close();
            db.close();
        }

        for (int i = 0; i < orderList.size(); i++) {
            ROSReturnOrder order = orderList.get(i);
            order.setOrderItems(getReturnOrderItems(context, order.getOrderId()));
        }

        return  orderList;
    }

    public ROSReturnOrder getReturnOrder(Context context, String orderId) {
        SQLiteDatabase db = getReadableDb(context);

        final String SQL_SELECT_RETURN_ORDER = "SELECT * FROM " + ROSDbConstants.ReturnOrder.TABLE_NAME +
                " WHERE " + ROSDbConstants.ReturnOrder.CL_NAME_ORDER_ID + " = '" + orderId + "';";
        Cursor c = db.rawQuery(SQL_SELECT_RETURN_ORDER, null);

        ROSReturnOrder order = null;

        if (c != null) {

            c.moveToFirst();
            order = new ROSReturnOrder();
            order.setCustomerId(c.getString(c.getColumnIndexOrThrow(ROSDbConstants.ReturnOrder.CL_NAME_CUSTOMER_ID)));
            order.setOrderId(c.getString(c.getColumnIndexOrThrow(ROSDbConstants.ReturnOrder.CL_NAME_ORDER_ID)));
            order.setOrderDate(c.getString(c.getColumnIndexOrThrow(ROSDbConstants.ReturnOrder.CL_NAME_ORDER_DATE)));
            order.setInvoiceNo(c.getString(c.getColumnIndexOrThrow(ROSDbConstants.ReturnOrder.CL_NAME_INVOICE_NO)));
            order.setDiscount(c.getDouble(c.getColumnIndexOrThrow(ROSDbConstants.ReturnOrder.CL_NAME_DISCOUNT)));
            order.setDiscountValue(c.getDouble(c.getColumnIndexOrThrow(ROSDbConstants.ReturnOrder.CL_NAME_DISCOUNT_VALUE)));
            order.setGrossValue(c.getDouble(c.getColumnIndexOrThrow(ROSDbConstants.ReturnOrder.CL_NAME_GROSS_VALUE)));
            order.setOrderValue(c.getDouble(c.getColumnIndexOrThrow(ROSDbConstants.ReturnOrder.CL_NAME_ORDER_VALUE)));
            order.setOrderStatus(c.getInt(c.getColumnIndexOrThrow(ROSDbConstants.ReturnOrder.CL_NAME_ORDER_STATUS)));

            c.close();
            db.close();
        }

        if (order != null) {
            order.setOrderItems(getReturnOrderItems(context, orderId));
        }

        return order;
    }

    public void updateReturnOrderStatus(Context context, String orderId, int orderStatus) {
        SQLiteDatabase db = getWritableDb(context);

        final String SQL_UPDATE_ORDER_STATUS = "UPDATE " + ROSDbConstants.ReturnOrder.TABLE_NAME +
                " SET " + ROSDbConstants.ReturnOrder.CL_NAME_ORDER_STATUS + " = " + orderStatus +
                " WHERE " + ROSDbConstants.ReturnOrder.CL_NAME_ORDER_ID + " = '" + orderId + "';";
        db.execSQL(SQL_UPDATE_ORDER_STATUS);
        db.close();
    }

    public String insertReturnOrder(Context context, ROSReturnOrder order) {
        SQLiteDatabase db = getWritableDb(context);

        ContentValues values = new ContentValues();
        values.put(ROSDbConstants.ReturnOrder.CL_NAME_CUSTOMER_ID, order.getCustomerId());
        values.put(ROSDbConstants.ReturnOrder.CL_NAME_ORDER_DATE, order.getOrderDate());
        values.put(ROSDbConstants.ReturnOrder.CL_NAME_DISCOUNT, order.getDiscount());
        values.put(ROSDbConstants.ReturnOrder.CL_NAME_DISCOUNT_VALUE, order.getDiscountValue());
        values.put(ROSDbConstants.ReturnOrder.CL_NAME_GROSS_VALUE, order.getGrossValue());
        values.put(ROSDbConstants.ReturnOrder.CL_NAME_ORDER_VALUE, order.getOrderValue());
        values.put(ROSDbConstants.ReturnOrder.CL_NAME_ORDER_STATUS, order.getOrderStatus());
        values.put(ROSDbConstants.ReturnOrder.CL_NAME_INVOICE_NO, order.getInvoiceNo());

        long orderId = db.insert(ROSDbConstants.ReturnOrder.TABLE_NAME, null, values);
        db.close();

        if (orderId == -1) {
            return null;
        }

        db = getWritableDb(context);
        String orderIdStr = "" + orderId;
        final String SQL_UPDATE_RETURN_ORDER = "UPDATE " + ROSDbConstants.ReturnOrder.TABLE_NAME +
                " SET " + ROSDbConstants.ReturnOrder.CL_NAME_ORDER_ID + " = '" + orderIdStr +
                "' WHERE " + ROSDbConstants.ReturnOrder._ID + " = " + orderId + ";";
        db.execSQL(SQL_UPDATE_RETURN_ORDER);
        db.close();

        //inserting order items
        boolean success = insertReturnOrderItems(context, orderIdStr, order.getOrderItems());
        if (!success) {
            db = getWritableDb(context);
            final String SQL_DELETE_RETURN_ORDER = "DELETE FROM " + ROSDbConstants.ReturnOrder.TABLE_NAME +
                    " WHERE " + ROSDbConstants.ReturnOrder._ID + " = " + orderId + ";";
            db.execSQL(SQL_DELETE_RETURN_ORDER);
            db.close();

            return null;
        }

        return orderIdStr;
    }

    public void clearReturnOrderTable(Context context) {
        SQLiteDatabase db = getWritableDb(context);

        final String SQL_DELETE_RETURN_ORDER = "DELETE FROM " + ROSDbConstants.ReturnOrder.TABLE_NAME + ";";
        db.execSQL(SQL_DELETE_RETURN_ORDER);
        db.close();
    }

    /*
    Return Order Item section
     */
    public ArrayList<ROSReturnOrderItem> getReturnOrderItems(Context context, String orderId) {
        SQLiteDatabase db = getReadableDb(context);

        final String SQL_SELECT_RETURN_ORDER_ITEMS = "SELECT * FROM " + ROSDbConstants.ReturnOrderItem.TABLE_NAME +
                " WHERE " + ROSDbConstants.ReturnOrderItem.CL_NAME_ORDER_ID + " = '" + orderId + "';";
        Cursor c = db.rawQuery(SQL_SELECT_RETURN_ORDER_ITEMS, null);

        ArrayList<ROSReturnOrderItem> orderItemList = new ArrayList<ROSReturnOrderItem>();
        if (c != null) {
            while (c.moveToNext()){
                ROSReturnOrderItem orderItem = new ROSReturnOrderItem();
                orderItem.setItemId(c.getString(c.getColumnIndexOrThrow(ROSDbConstants.ReturnOrderItem.CL_NAME_ITEM_ID)));
                orderItem.setOrderId(c.getString(c.getColumnIndexOrThrow(ROSDbConstants.ReturnOrderItem.CL_NAME_ORDER_ID)));
                orderItem.setProductId(c.getString(c.getColumnIndexOrThrow(ROSDbConstants.ReturnOrderItem.CL_NAME_PRODUCT_ID)));
                orderItem.setProductName(c.getString(c.getColumnIndexOrThrow(ROSDbConstants.ReturnOrderItem.CL_NAME_PRODUCT_NAME)));
                orderItem.setBatchName(c.getString(c.getColumnIndexOrThrow(ROSDbConstants.ReturnOrderItem.CL_NAME_BATCH_NAME)));
                orderItem.setPrice(c.getDouble(c.getColumnIndexOrThrow(ROSDbConstants.ReturnOrderItem.CL_NAME_PRICE)));
                orderItem.setDiscount(c.getDouble(c.getColumnIndexOrThrow(ROSDbConstants.ReturnOrderItem.CL_NAME_DISCOUNT)));
                orderItem.setItemValue(c.getDouble(c.getColumnIndexOrThrow(ROSDbConstants.ReturnOrderItem.CL_NAME_ITEM_VALUE)));
                orderItem.setQuantity(c.getInt(c.getColumnIndexOrThrow(ROSDbConstants.ReturnOrderItem.CL_NAME_QUANTITY)));
                orderItem.setFreeIssues(c.getInt(c.getColumnIndexOrThrow(ROSDbConstants.ReturnOrderItem.CL_NAME_FREE_ISSUES)));
                orderItemList.add(orderItem);
            }

            c.close();
            db.close();
        }

        return orderItemList;
    }

    protected boolean insertReturnOrderItems(Context context, String orderId, ArrayList<ROSReturnOrderItem> items) {

        SQLiteDatabase db = getWritableDb(context);
        boolean success = true;

        for (int i = 0; i < items.size(); i++) {

            ROSReturnOrderItem orderItem = items.get(i);

            ContentValues values = new ContentValues();
            values.put(ROSDbConstants.ReturnOrderItem.CL_NAME_ORDER_ID, orderId);
            values.put(ROSDbConstants.ReturnOrderItem.CL_NAME_PRODUCT_ID, orderItem.getProductId());
            values.put(ROSDbConstants.ReturnOrderItem.CL_NAME_PRODUCT_NAME, orderItem.getProductName());
            values.put(ROSDbConstants.ReturnOrderItem.CL_NAME_BATCH_NAME, orderItem.getBatchName());
            values.put(ROSDbConstants.ReturnOrderItem.CL_NAME_PRICE, orderItem.getPrice());
            values.put(ROSDbConstants.ReturnOrderItem.CL_NAME_DISCOUNT, orderItem.getDiscount());
            values.put(ROSDbConstants.ReturnOrderItem.CL_NAME_ITEM_VALUE, orderItem.getItemValue());
            values.put(ROSDbConstants.ReturnOrderItem.CL_NAME_QUANTITY, orderItem.getQuantity());
            values.put(ROSDbConstants.ReturnOrderItem.CL_NAME_FREE_ISSUES, orderItem.getFreeIssues());

            long itemId = db.insert(ROSDbConstants.ReturnOrderItem.TABLE_NAME, null, values);
            if (itemId == -1) {
                success = false;
                break;
            }
        }
        db.close();

        if (!success) {
            deleteReturnOrderItems(context, orderId);
        }

        return success;
    }

    protected void deleteReturnOrderItems(Context context, String orderId) {
        SQLiteDatabase db = getWritableDb(context);

        final String SQL_DELETE_RETURN_ORDER_ITEMS = "DELETE FROM " + ROSDbConstants.ReturnOrderItem.TABLE_NAME
                + " WHERE " + ROSDbConstants.ReturnOrderItem.CL_NAME_ORDER_ID + " = '" + orderId + "';";
        db.execSQL(SQL_DELETE_RETURN_ORDER_ITEMS);
        db.close();
    }

    public void clearReturnOrderItemTable(Context context) {
        SQLiteDatabase db = getWritableDb(context);

        final String SQL_DELETE_RETURN_ORDER_ITEMS = "DELETE FROM " + ROSDbConstants.ReturnOrderItem.TABLE_NAME + ";";
        db.execSQL(SQL_DELETE_RETURN_ORDER_ITEMS);
        db.close();
    }

    /*
    Stock section
     */

    public ArrayList<ROSStock> getStocks(Context context) {

        SQLiteDatabase db = getReadableDb(context);

        final String SQL_SELECT_STOCK = "SELECT * FROM " + ROSDbConstants.Stock.TABLE_NAME + ";";
        Cursor c = db.rawQuery(SQL_SELECT_STOCK, null);

        ArrayList<ROSStock> stockList = new ArrayList<ROSStock>();

        if (c != null) {
            while (c.moveToNext()) {
                ROSStock stock = new ROSStock();
                stock.setProductDescription(c.getString(c.getColumnIndexOrThrow(ROSDbConstants.Stock.CL_NAME_PRODUCT_NAME)));
                stock.setProductBatchCode(c.getString(c.getColumnIndexOrThrow(ROSDbConstants.Stock.CL_NAME_BATCH_NAME)));
                stock.setBrandName(c.getString(c.getColumnIndexOrThrow(ROSDbConstants.Stock.CL_NAME_BRAND_NAME)));
                stock.setAgenName(c.getString(c.getColumnIndexOrThrow(ROSDbConstants.Stock.CL_NAME_AGENCY_NAME)));
                stock.setQuntityInStock(c.getInt(c.getColumnIndexOrThrow(ROSDbConstants.Stock.CL_NAME_ALLOCATED_QTY)));
                stock.setAvailableQuantity(c.getInt(c.getColumnIndexOrThrow(ROSDbConstants.Stock.CL_NAME_AVAILABLE_QTY)));
                stock.setStatus(c.getInt(c.getColumnIndexOrThrow(ROSDbConstants.Stock.CL_NAME_STATUS)));
                stock.setAgenCode(c.getString(c.getColumnIndexOrThrow(ROSDbConstants.Stock.CL_NAME_AGENT_CODE)));
                stock.setBrandCode(c.getString(c.getColumnIndexOrThrow(ROSDbConstants.Stock.CL_NAME_BRAND_CODE)));
                stock.setProductCode(c.getString(c.getColumnIndexOrThrow(ROSDbConstants.Stock.CL_NAME_PRODUCT_CODE)));
                stock.setCompCode(c.getString(c.getColumnIndexOrThrow(ROSDbConstants.Stock.CL_NAME_COMP_CODE)));
                stock.setDistributorCode(c.getString(c.getColumnIndexOrThrow(ROSDbConstants.Stock.CL_NAME_DISTRIB_CODE)));
                stock.setUnitPrice(c.getDouble(c.getColumnIndexOrThrow(ROSDbConstants.Stock.CL_NAME_UNIT_PRICE)));

                stockList.add(stock);
            }
            c.close();
            db.close();
        }

        return stockList;
    }

    public ArrayList<ROSStock> getStocks(Context context, int status) {

        SQLiteDatabase db = getReadableDb(context);

        final String SQL_SELECT_STOCK = "SELECT * FROM " + ROSDbConstants.Stock.TABLE_NAME +
                " WHERE " + ROSDbConstants.Stock.CL_NAME_STATUS + " = " + status + ";";
        Cursor c = db.rawQuery(SQL_SELECT_STOCK, null);

        ArrayList<ROSStock> stockList = new ArrayList<ROSStock>();

        if (c != null) {
            while (c.moveToNext()) {
                ROSStock stock = new ROSStock();
                stock.setProductDescription(c.getString(c.getColumnIndexOrThrow(ROSDbConstants.Stock.CL_NAME_PRODUCT_NAME)));
                stock.setProductBatchCode(c.getString(c.getColumnIndexOrThrow(ROSDbConstants.Stock.CL_NAME_BATCH_NAME)));
                stock.setBrandName(c.getString(c.getColumnIndexOrThrow(ROSDbConstants.Stock.CL_NAME_BRAND_NAME)));
                stock.setAgenName(c.getString(c.getColumnIndexOrThrow(ROSDbConstants.Stock.CL_NAME_AGENCY_NAME)));
                stock.setQuntityInStock(c.getInt(c.getColumnIndexOrThrow(ROSDbConstants.Stock.CL_NAME_ALLOCATED_QTY)));
                stock.setAvailableQuantity(c.getInt(c.getColumnIndexOrThrow(ROSDbConstants.Stock.CL_NAME_AVAILABLE_QTY)));
                stock.setStatus(c.getInt(c.getColumnIndexOrThrow(ROSDbConstants.Stock.CL_NAME_STATUS)));
                stock.setAgenCode(c.getString(c.getColumnIndexOrThrow(ROSDbConstants.Stock.CL_NAME_AGENT_CODE)));
                stock.setBrandCode(c.getString(c.getColumnIndexOrThrow(ROSDbConstants.Stock.CL_NAME_BRAND_CODE)));
                stock.setProductCode(c.getString(c.getColumnIndexOrThrow(ROSDbConstants.Stock.CL_NAME_PRODUCT_CODE)));
                stock.setCompCode(c.getString(c.getColumnIndexOrThrow(ROSDbConstants.Stock.CL_NAME_COMP_CODE)));
                stock.setDistributorCode(c.getString(c.getColumnIndexOrThrow(ROSDbConstants.Stock.CL_NAME_DISTRIB_CODE)));
                stock.setUnitPrice(c.getDouble(c.getColumnIndexOrThrow(ROSDbConstants.Stock.CL_NAME_UNIT_PRICE)));
                stockList.add(stock);
            }
            c.close();
            db.close();
        }

        return stockList;
    }

    public void insertStocks(Context context, ArrayList<ROSStock> stocks) {
        SQLiteDatabase db = getWritableDb(context);

        for (int i = 0; i < stocks.size(); i++) {
            ROSStock stock = stocks.get(i);

            ContentValues values = new ContentValues();
            values.put(ROSDbConstants.Stock.CL_NAME_PRODUCT_NAME, stock.getProductDescription());
            values.put(ROSDbConstants.Stock.CL_NAME_BATCH_NAME, stock.getProductBatchCode());
            values.put(ROSDbConstants.Stock.CL_NAME_BRAND_NAME, stock.getBrandName());
            values.put(ROSDbConstants.Stock.CL_NAME_AGENCY_NAME, stock.getAgenName());
            values.put(ROSDbConstants.Stock.CL_NAME_ALLOCATED_QTY, stock.getQuntityInStock());
            values.put(ROSDbConstants.Stock.CL_NAME_AVAILABLE_QTY, stock.getAvailableQuantity());
            values.put(ROSDbConstants.Stock.CL_NAME_STATUS, stock.getStatus());

            db.insert(ROSDbConstants.Stock.TABLE_NAME, null, values);
        }

        db.close();
    }

    public void insertStock(Context context, ROSStock stock) {
        SQLiteDatabase db = getWritableDb(context);

        ContentValues values = new ContentValues();
        values.put(ROSDbConstants.Stock.CL_NAME_PRODUCT_NAME, stock.getProductDescription());
        values.put(ROSDbConstants.Stock.CL_NAME_BATCH_NAME, stock.getProductBatchCode());
        values.put(ROSDbConstants.Stock.CL_NAME_BRAND_NAME, stock.getBrandName());
        values.put(ROSDbConstants.Stock.CL_NAME_AGENCY_NAME, stock.getAgenName());
        values.put(ROSDbConstants.Stock.CL_NAME_ALLOCATED_QTY, stock.getQuntityInStock());
        values.put(ROSDbConstants.Stock.CL_NAME_AVAILABLE_QTY, stock.getAvailableQuantity());
        values.put(ROSDbConstants.Stock.CL_NAME_STATUS, stock.getStatus());
        values.put(ROSDbConstants.Stock.CL_NAME_AGENT_CODE, stock.getAgenCode());
        values.put(ROSDbConstants.Stock.CL_NAME_BRAND_CODE, stock.getBrandCode());
        values.put(ROSDbConstants.Stock.CL_NAME_PRODUCT_CODE, stock.getProductCode());
        values.put(ROSDbConstants.Stock.CL_NAME_COMP_CODE, stock.getCompCode());
        values.put(ROSDbConstants.Stock.CL_NAME_DISTRIB_CODE, stock.getDistributorCode());
        values.put(ROSDbConstants.Stock.CL_NAME_UNIT_PRICE, stock.getUnitPrice());

        db.insert(ROSDbConstants.Stock.TABLE_NAME, null, values);
        db.close();
    }

    public void updateStock(Context context, ROSStock stock) {
        SQLiteDatabase db = getWritableDb(context);

        final String SQL_UPDATE_STOCK = "UPDATE " + ROSDbConstants.Stock.TABLE_NAME +
                " SET " + ROSDbConstants.Stock.CL_NAME_AVAILABLE_QTY + " = " + stock.getAvailableQuantity() +
                " WHERE " + ROSDbConstants.Stock.CL_NAME_PRODUCT_NAME + " = '" + stock.getProductDescription() +
                "' AND " + ROSDbConstants.Stock.CL_NAME_BATCH_NAME + " = '" + stock.getProductBatchCode() + "';";
        db.execSQL(SQL_UPDATE_STOCK);
        db.close();
    }

    public void insertReturnStock(Context context, ROSStock stock) {
        SQLiteDatabase db = getWritableDb(context);

        ContentValues values = new ContentValues();
        values.put(ROSDbConstants.Stock.CL_NAME_PRODUCT_NAME, stock.getProductDescription());
        values.put(ROSDbConstants.Stock.CL_NAME_BATCH_NAME, stock.getProductBatchCode());
        values.put(ROSDbConstants.Stock.CL_NAME_BRAND_NAME, stock.getBrandName());
        values.put(ROSDbConstants.Stock.CL_NAME_AGENCY_NAME, stock.getAgenName());
        values.put(ROSDbConstants.Stock.CL_NAME_ALLOCATED_QTY, stock.getQuntityInStock());
        values.put(ROSDbConstants.Stock.CL_NAME_AVAILABLE_QTY, stock.getAvailableQuantity());
        values.put(ROSDbConstants.Stock.CL_NAME_STATUS, 1);
        values.put(ROSDbConstants.Stock.CL_NAME_AGENT_CODE, stock.getAgenCode());
        values.put(ROSDbConstants.Stock.CL_NAME_BRAND_CODE, stock.getBrandCode());
        values.put(ROSDbConstants.Stock.CL_NAME_PRODUCT_CODE, stock.getProductCode());
        values.put(ROSDbConstants.Stock.CL_NAME_COMP_CODE, stock.getCompCode());
        values.put(ROSDbConstants.Stock.CL_NAME_DISTRIB_CODE, stock.getDistributorCode());
        values.put(ROSDbConstants.Stock.CL_NAME_UNIT_PRICE, stock.getUnitPrice());

        db.insert(ROSDbConstants.Stock.TABLE_NAME, null, values);
        db.close();
    }

    public void clearStockTable(Context context) {
        SQLiteDatabase db = getWritableDb(context);

        final String SQL_DELETE_STOCK = "DELETE FROM " + ROSDbConstants.Stock.TABLE_NAME + ";";
        db.execSQL(SQL_DELETE_STOCK);
        db.close();
    }

    /*
    Product section
     */
    public ArrayList<ROSProduct> getProducts(Context context) {

        SQLiteDatabase db = getReadableDb(context);

        final String SQL_SELECT_PRODUCT = "SELECT * FROM " + ROSDbConstants.Product.TABLE_NAME + ";";
        Cursor c = db.rawQuery(SQL_SELECT_PRODUCT, null);

        ArrayList<ROSProduct> productList = new ArrayList<ROSProduct>();

        if (c != null) {
            while (c.moveToNext()) {
                ROSProduct product = new ROSProduct();
                product.setProductName(c.getString(c.getColumnIndexOrThrow(ROSDbConstants.Product.CL_NAME_PRODUCT_NAME)));
                product.setBatchName(c.getString(c.getColumnIndexOrThrow(ROSDbConstants.Product.CL_NAME_BATCH_NAME)));
                product.setBrandName(c.getString(c.getColumnIndexOrThrow(ROSDbConstants.Product.CL_NAME_BRAND_NAME)));
                product.setAgencyName(c.getString(c.getColumnIndexOrThrow(ROSDbConstants.Product.CL_NAME_AGENCY_NAME)));
                productList.add(product);
            }
            c.close();
            db.close();
        }

        return productList;
    }

    public void insertProducts(Context context, ArrayList<ROSProduct> products) {
        SQLiteDatabase db = getWritableDb(context);

        for (int i = 0; i < products.size(); i++) {
            ROSProduct product = products.get(i);
            ContentValues values = new ContentValues();
            values.put(ROSDbConstants.Product.CL_NAME_PRODUCT_NAME, product.getProductName());
            values.put(ROSDbConstants.Product.CL_NAME_BATCH_NAME, product.getBatchName());
            values.put(ROSDbConstants.Product.CL_NAME_BRAND_NAME, product.getBrandName());
            values.put(ROSDbConstants.Product.CL_NAME_AGENCY_NAME, product.getAgencyName());

            db.insert(ROSDbConstants.Product.TABLE_NAME, null, values);
        }

        db.close();
    }

    public void clearProductTable(Context context) {
        SQLiteDatabase db = getWritableDb(context);

        final String SQL_DELETE_PRODUCT = "DELETE FROM " + ROSDbConstants.Product.TABLE_NAME + ";";
        db.execSQL(SQL_DELETE_PRODUCT);
        db.close();
    }

    /*
    Temp data
     */
    public void addTempData(Context context) {
        if (dbCreated) {
            addCustomers(context);
            addStocks(context);
            addProducts(context);
        }
    }

    public void addCustomers(Context context) {
        for (int i = 0; i < 20; i++) {
            ROSCustomer customer = new ROSCustomer();
            customer.setCustomerId("" + (i+1)*5);
            customer.setFirstName("Temp customer " + (i+1));
            customer.setLastName("");
            customer.setShopName("Shop " + (i+1));
            customer.setTown("Town " + (i+1));
            customer.setTownId("" + (i+1)*5);
            customer.setOutstanding((i+1)*1000);

            insertCustomer(context, customer);
        }
    }

    public void addStocks(Context context) {
        for (int i = 0; i < 50; i++) {
            ROSStock stock = new ROSStock();
            stock.setProductDescription("Product " + (i + 1));
            stock.setProductBatchCode("Batch " + (i + 1));
            stock.setBrandName("Brand " + (i + 1));
            stock.setAgenName("Agency " + (i + 1));
            stock.setQuntityInStock((i + 1) * 100);
            stock.setAvailableQuantity((i + 1) * 100);

            insertStock(context, stock);
        }
    }

    public void addProducts(Context context) {

        ArrayList<ROSProduct> products = new ArrayList<ROSProduct>();

        for (int i = 0; i < 200; i++) {
            ROSProduct product = new ROSProduct();
            product.setProductName("Product " + (i+1));
            product.setBatchName("Batch " + (i+1));
            product.setBrandName("Brand " + (i+1));
            product.setAgencyName("Agency " + (i+1));
            products.add(product);
        }

        insertProducts(context, products);
    }
}
