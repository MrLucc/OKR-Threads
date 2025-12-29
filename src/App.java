private static final int NUM_TASKS = 5;
private static final int PROCESSING_TIME_MS = 1000; // 1 segundo

void main() throws Exception {
    IO.println("=== Demonstração de Threads em Java ===\n");

    List<TaskProcessor> tasks = criarTarefas();

    IO.println("--- 1. EXECUÇÃO SEQUENCIAL ---");
    long inicioSequencial = System.currentTimeMillis();
    executarSequencial(tasks);
    long tempoSequencial = System.currentTimeMillis() - inicioSequencial;
    IO.println("Tempo total sequencial: " + tempoSequencial + "ms\n");

    Thread.sleep(500);

    IO.println("--- 2. EXECUÇÃO COM THREADS BÁSICAS ---");
    long inicioThreads = System.currentTimeMillis();
    executarComThreads(tasks);
    long tempoThreads = System.currentTimeMillis() - inicioThreads;
    IO.println("Tempo total com threads: " + tempoThreads + "ms\n");

    Thread.sleep(500);

    IO.println("--- 3. EXECUÇÃO COM THREAD POOL ---");
    long inicioThreadPool = System.currentTimeMillis();
    executarComThreadPool(tasks);
    long tempoThreadPool = System.currentTimeMillis() - inicioThreadPool;
    IO.println("Tempo total com ThreadPool: " + tempoThreadPool + "ms\n");


    IO.println("=== RESUMO DOS RESULTADOS ===");
    IO.println("Sequencial: " + tempoSequencial + "ms");
    IO.println("Threads básicas: " + tempoThreads + "ms");
    IO.println("ThreadPool: " + tempoThreadPool + "ms");
}


private static List<TaskProcessor> criarTarefas() {

    List<TaskProcessor> tasks = new ArrayList<>();
    for (int i = 0; i < NUM_TASKS; i++) {
        tasks.add(new TaskProcessor(i, PROCESSING_TIME_MS));
    }
    return tasks;
}


private static void executarSequencial(List<TaskProcessor> tasks) {

    for (TaskProcessor task : tasks) {
        task.run();
    }

}


private static void executarComThreads(List<TaskProcessor> tasks) throws InterruptedException {

    List<Thread> threads = new ArrayList<>();
    for (TaskProcessor task : tasks) {
        Thread thread = new Thread(task);
        thread.setName("Thread-Tarefa-" + task.getTaskId());
        threads.add(thread);
        thread.start();
    }
    for (Thread thread2 : threads) {
        thread2.join();
    }

}


private static void executarComThreadPool(List<TaskProcessor> tasks) throws InterruptedException {

    ExecutorService executorService = Executors.newFixedThreadPool(3);
    for (TaskProcessor task : tasks) {
        executorService.submit(task);
    }
    executorService.shutdown();
    if (!executorService.awaitTermination(10, TimeUnit.SECONDS)) {
        executorService.shutdownNow();
        throw new RuntimeException("ThreadPool não terminou em 10 segundoos");
    }
}
