package com.scaler.LLD.Module_2.Class_6.Facade;

/**
 * Facade, before and after:
 *  1. Without a facade, every client repeats the steps -- and a new step has to be added everywhere
 *  2. With the facade, a new step is added once
 *  3. Beyond the class: a facade doesn't lock the subsystems away
 */
public class FacadeEvolutionDemo {

    /** A new step the business adds later. */
    static class PaymentService {
        void charge(String customerName) {
            System.out.println("  [Payment]   Charging " + customerName);
        }
    }

    /** Client A was updated when payment was introduced... */
    static void websiteCheckoutWithoutFacade(String orderId, String customer) {
        new PaymentService().charge(customer);
        new WarehouseManagementService().updateInventory("ITEM-IPHONE");
        new InvoiceService().generateInvoice(customer, "iPhone: 1");
        new LogisticsService().initiateShipping(orderId);
    }

    /** ...Client B was missed: same flow, copy-pasted, without the new step. */
    static void mobileCheckoutWithoutFacade(String orderId, String customer) {
        new WarehouseManagementService().updateInventory("ITEM-KINDLE");
        new InvoiceService().generateInvoice(customer, "Kindle: 1");
        new LogisticsService().initiateShipping(orderId);
    }

    /** The facade after the change: the new step lives in ONE place. */
    static class AmazonWithPayment {
        private final PaymentService paymentService = new PaymentService();
        private final WarehouseManagementService warehouse = new WarehouseManagementService();
        private final InvoiceService invoiceService = new InvoiceService();
        private final LogisticsService logisticsService = new LogisticsService();

        void placeOrder(String orderId, String customerName, String itemId, String orderDetails) {
            paymentService.charge(customerName);
            warehouse.updateInventory(itemId);
            invoiceService.generateInvoice(customerName, orderDetails);
            logisticsService.initiateShipping(orderId);
        }
    }

    public static void main(String[] args) {
        System.out.println("=== 1. Without a facade: the steps are copied into every client ===");
        System.out.println("website:");
        websiteCheckoutWithoutFacade("ORD-1", "Rehber");
        System.out.println("mobile app:");
        mobileCheckoutWithoutFacade("ORD-2", "Aman");
        System.out.println("  -> Aman's order shipped without being charged: one client missed the new step");

        System.out.println("\n=== 2. With a facade: add the step once, every client gets it ===");
        AmazonWithPayment amazon = new AmazonWithPayment();
        System.out.println("website:");
        amazon.placeOrder("ORD-3", "Rehber", "ITEM-IPHONE", "iPhone: 1");
        System.out.println("mobile app:");
        amazon.placeOrder("ORD-4", "Aman", "ITEM-KINDLE", "Kindle: 1");

        System.out.println("\n=== 3. Beyond the class: the subsystems are still usable directly ===");
        System.out.println("  e.g. a warehouse admin tool that only restocks:");
        new WarehouseManagementService().updateInventory("ITEM-IPHONE");
        System.out.println("  a facade adds a simple entry point; it doesn't hide or forbid the detailed one");
    }
}
