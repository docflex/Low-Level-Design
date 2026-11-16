package com.scaler.LLD.Module_2.Class_6.Facade;

/**
 * Facade: one simple placeOrder() call over three subsystems. Clients no longer need to know
 * the steps, their order, or which service does what. If a step is added or changed,
 * only this class changes.
 */
public class Amazon {
    private final WarehouseManagementService warehouseManagementService;
    private final InvoiceService invoiceService;
    private final LogisticsService logisticsService;

    public Amazon() {
        this(new WarehouseManagementService(), new InvoiceService(), new LogisticsService());
    }

    /** Lets a test pass in fake subsystems. */
    public Amazon(WarehouseManagementService warehouseManagementService,
                  InvoiceService invoiceService,
                  LogisticsService logisticsService) {
        this.warehouseManagementService = warehouseManagementService;
        this.invoiceService = invoiceService;
        this.logisticsService = logisticsService;
    }

    public void placeOrder(String orderId, String customerName, String itemId, String orderDetails) {
        warehouseManagementService.updateInventory(itemId);
        invoiceService.generateInvoice(customerName, orderDetails);
        logisticsService.initiateShipping(orderId);
    }
}
