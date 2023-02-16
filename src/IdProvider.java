public class IdProvider {
    private static volatile IdProvider instance = null;
    private static Integer currentId = 0;

    private IdProvider() {}

    // Thread-safe singleton
    public static IdProvider getInstance() {
        if (instance == null) {
            synchronized(IdProvider.class) {
                if (instance == null) {
                    instance = new IdProvider();
                }
            }
        }
        return instance;
    }

    public synchronized void giveId(Product product) {
        product.setId(currentId);
        currentId++;
    }
}