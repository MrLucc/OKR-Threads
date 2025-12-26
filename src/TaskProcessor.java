public class TaskProcessor implements Runnable {
    
    private int taskId;
  
    private int processingTime;
   
    public TaskProcessor(int taskId, int processingTime) {
        this.taskId = taskId;
        this.processingTime = processingTime;
    }

    @Override
    public void run() {
      System.out.println("Thread " + Thread.currentThread().getName() + " Iniciando processamento da tarefa #" + taskId);
        try {
            Thread.sleep(processingTime);
            System.out.println("Thread " + Thread.currentThread().getName() + " Concluindo processamento da tarefa #" + taskId);
        } catch (InterruptedException error) {
            System.out.println("Tarefa #" + taskId + " foi interrompida!");
            Thread.currentThread().interrupt();
        }
    }
   
    
    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public int getProcessingTime() {
        return processingTime;
    }

    public void setProcessingTime(int processingTime) {
        this.processingTime = processingTime;
    }

    public int getTaskId(){
        return taskId;
    }
}
