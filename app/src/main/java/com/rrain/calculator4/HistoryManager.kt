package com.rrain.calculator4

import androidx.lifecycle.LiveData
import com.rrain.calculator4.calculator.Calculator
import com.rrain.calculator4.db.HistoryEntry
import com.rrain.calculator4.db.HistoryEntryDao

class HistoryManager(private val dao: HistoryEntryDao) {
  //@Getter private List<HistoryEntry> historyList;
  private val historyLive: LiveData<List<HistoryEntry>>
  fun getHistoryLive() = historyLive
  
  init {
    // historyList = dao.getAllSortedByDate();
    historyLive = dao.allLive
    // historyLive.observeForever(list -> new Thread(()->trimToSize(list)).start());
  }
  
  /**
   *
   * @param entry - entry to save.
   * If last saved entry's expression and result are equals to this entry's ones, it doesn't save
   * @return true if saved
   */
  fun add(entry: HistoryEntry) {
    Thread(Runnable {
      val e: HistoryEntry?
      e = dao.first
      if (e != null && entry.getExpression() == e.getExpression() && entry.getResult() == e.getResult()) return@Runnable
      dao.insert(entry)
      trimToSize()
    }).start()
    
    /*List<HistoryEntry> entries;
        if (entry==null || ((entries=historyLive.getValue())!=null && entries.size()!=0 && entry.getExpression().equals(historyLive.getValue().get(0).getExpression()) && entry.getResult().equals(historyLive.getValue().get(0).getResult()))) return false;
        //historyList.add(entry);
        new Thread(() -> dao.insert(entry)).start();
        //trimToSize();
        return true;*/
  }
  
  fun delete(entry: HistoryEntry) {
    Thread(Runnable { dao.delete(entry) }).start()
    // historyList.remove(idx);
  }
  
  fun clear() {
    Thread(Runnable { dao.clear() }).start()
    // historyList.clear();
  }
  
  /*private void trimToSize(){
        if(Calculator.enableAutodeleteOfHistory){
            while (historyList.size() > Calculator.historyLimit){
                dao.delete(historyList.get(historyList.size()-1));
                historyList.remove(historyList.get(historyList.size()-1));
            }
        }
    }*/
  private fun trimToSize() {
    if (Calculator.enableAutodeleteOfHistory) {
      while (dao.size() > Calculator.historyLimit) {
        dao.delete(dao.last!!)
      }
    }
  }
} /*public static final String SAVED_HISTORY_CONTAINER = "History container";
    public static ArrayList<String[]> historyContainer; //String[]{expression, result}
    public static int historyContainerHashCode;

    public static void loadHistory(){
        try {
            FileInputStream fis = App.Companion.getApp().openFileInput(SAVED_HISTORY_CONTAINER);
            ObjectInputStream oin = new ObjectInputStream(fis);
            historyContainer = (ArrayList<String[]>) oin.readObject();
            fis.close();
        } catch (Exception e) {historyContainer = new ArrayList<>();}
        historyContainerHashCode = historyContainer.hashCode();
    }

    public static void saveHistory(){
        if (historyContainer.hashCode() != historyContainerHashCode)
            try {
                FileOutputStream fos = App.Companion.getApp().openFileOutput(SAVED_HISTORY_CONTAINER, Context.MODE_PRIVATE);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(historyContainer);
                oos.flush();
                fos.close();
            } catch (IOException e) {*/
/*e.printStackTrace();*/ /*}
    }

    public static void clear(){
        historyContainer.clear();
    }

    */
/*
 * @return true if saved
 */
/*
    public static boolean checkAndSaveToHistory(String expression, String result){
        if (historyContainer.size()!=0) if (historyContainer.get(0)[0].equals(expression)) return false;

        saveToHistory(expression, result);
        return true;
    }

    public static void saveToHistory(String expression, String result){
        historyContainer.add(0, new String[]{expression, result});
        if(Calculator.enableAutodeleteOfHistory) {
            while (historyContainer.size() > Calculator.historyLimit) {
                historyContainer.remove(historyContainer.size()-1);
            }
        }
    }
*/

