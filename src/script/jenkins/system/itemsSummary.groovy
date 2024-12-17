/* groovylint-disable CompileStatic, DuplicateNumberLiteral */
//https://stackoverflow.com/questions/159148/groovy-executing-shell-commands
package script.jenkins.system

Jenkins jenkins = Jenkins.getInstanceOrNull();
Map < String, Integer > containerCounts = new TreeMap < > ();
Map < String, Stats > jobStats = new HashMap < > ();
Stats jobTotal = new Stats();
Map < String, Stats > containerStats = new HashMap < > ();
Integer counter = 0 
jenkins.allItems().each{item ->
  String key = item.getClass().getName();
  Integer cnt = containerCounts.get(key);
  containerCounts.put(key, cnt == null ? 1 : cnt + 1);
  if (item instanceof Job) {
    Job < ? , ? > j = (Job) item;
    Stats s = jobStats.get(key);
    if (s == null) {
      jobStats.put(key, s = new Stats());
    }
  }
  if (item instanceof ItemGroup) {
    Stats s = containerStats.get(key);
    if (s == null) {
      containerStats.put(key, s = new Stats());
    }
    s.add(((ItemGroup) item).getItems().size());
  }
}
out.println("Item statistics");
out.println("===============");
out.println();
for (Map.Entry < String, Integer > entry: containerCounts.entrySet()) {
  String key = entry.getKey();
  out.println("  * `" + key + "`");
  out.println("    - Number of items: " + entry.getValue());
  counter = counter + entry.getValue()
}
out.println();
out.println("Total job statistics");
out.println("======================");
out.println();
out.println("  * Number of items: " + counter);

class Stats {
  private int count = 0;
  private long sumOfValues = 0;
  private long sumOfSquaredValues = 0;

  public synchronized void add(int x) {
    count++;
    sumOfValues += x;
    sumOfSquaredValues += x * (long) x;
  }

  /**
   * Compute the mean
   * @return the mean
   */
  public synchronized double mean() {
    return sumOfValues / (double) count;
  }

  private static double roundToSigFig(double num, int sigFig) {
    if (num == 0) {
      return 0;
    }
    final double d = Math.ceil(Math.log10(num < 0 ? -num : num));
    final int pow = sigFig - (int) d;
    final double mag = Math.pow(10, pow);
    final long shifted = Math.round(num * mag);
    return shifted / mag;
  }

  /**
   * Compute the Standard Deviation (or Variance) as a measure of dispersion
   * @return the standard deviation
   */
  public synchronized double standardDeviation() {
    if (count >= 2) {
      double v = Math.sqrt((count * (double) sumOfSquaredValues - sumOfValues * (double) sumOfValues) /
        count /
        (count - 1));
      if (count <= 100) {
        return roundToSigFig(v, 1); // 0.88*SD to 1.16*SD
      }
      if (count <= 1000) {
        return roundToSigFig(v, 2); // 0.96*SD to 1.05*SD
      }
      return v;
    } else {
      return Double.NaN;
    }
  }

  public synchronized String toString() {
    if (count == 0) {
      return "N/A";
    }
    if (count == 1) {
      return sumOfValues + " [n=" + count + "]";
    }
    return mean() + " [n=" + count + ", s=" + standardDeviation() + "]";
  }
}