package com.scaler.LLD.Module_2.Class_6.Facade;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Facade: one simple placeOrder() call over three subsystems. Clients no longer need to know
 * the steps, their order, or which service does what. If a step is added or changed,
 * only this class changes.
 *
 * Also the Subject for the Observer pattern (Module 2, Class 8): services that merely need to
 * KNOW an order was placed (loyalty, recommendations, ...) subscribe instead of being hard-coded
 * into placeOrder().
 */
public class Amazon {
    private final WarehouseManagementService warehouseManagementService;
    private final InvoiceService invoiceService;
    private final LogisticsService logisticsService;

    // Copy-on-write: subscribers may (un)subscribe while we're notifying
    private final List<PlaceOrderSubscriber> subscriberList;

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
        subscriberList = new CopyOnWriteArrayList<>();
    }

    public void subscribe(PlaceOrderSubscriber placeOrderSubscriber) {
        if (!subscriberList.contains(placeOrderSubscriber)) {
            subscriberList.add(placeOrderSubscriber);
        }
    }

    public void unsubscribe(PlaceOrderSubscriber placeOrderSubscriber) {
        subscriberList.remove(placeOrderSubscriber);
    }

    public void placeOrder(String orderId, String customerName, String itemId, String orderDetails) {
        warehouseManagementService.updateInventory(itemId);
        invoiceService.generateInvoice(customerName, orderDetails);
        logisticsService.initiateShipping(orderId);
        notifyAllSubscribers(customerName);
    }

    /**
     * Private: only a real order should notify subscribers. One failing subscriber must not stop
     * the others -- or make placeOrder() fail for an order that was already placed.
     */
    private void notifyAllSubscribers(String customerName) {
        for (PlaceOrderSubscriber placeOrderSubscriber : subscriberList) {
            try {
                placeOrderSubscriber.onOrderPlaced(customerName);
            } catch (RuntimeException e) {
                System.out.println("  (subscriber " + placeOrderSubscriber.getClass().getSimpleName()
                        + " failed: " + e.getMessage() + " -- others still notified)");
            }
        }
    }
}
