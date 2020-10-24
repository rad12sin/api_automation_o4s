package listener;


import org.testng.*;
import org.testng.collections.Lists;
import org.testng.internal.Utils;
import org.testng.xml.XmlSuite;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;


public class CustomReport implements IReporter {
  private static final SimpleDateFormat dateFormatter = new SimpleDateFormat(
          " MMM d 'at' hh:mm a");
  private PrintWriter m_out;
  private int m_row;
  private Integer m_testIndex;
  private int m_methodIndex;
  private Scanner scanner;
  private String OUT_FOLDER = "./CustomReports";
  private String Test_Output_Folder = "./tests-output";
  private static String suiteName = "";

  /**
   * This method is the entry point of this class. TestNG calls this listener
   * method to generate the report.
   */
  @Override
  public void generateReport(List<XmlSuite> xml, List<ISuite> suites,
                             String outdir) {
    Reporter.log("", true);
    Reporter.log("-------------------------------------", true);
    Reporter.log("-- Generating tests HTML report...  --", true);
    Reporter.log("-------------------------------------", true);
    // Iterating over each suite included in the tests
    int failedTests = 0; // Record only the ones failed in Prod Monitoring
    // suite.
    int pass = 0;
    int skip = 0;
    int total = 0;

    for (ISuite suite : suites) {
      // Following code gets the suite name
      suiteName = suite.getName();
      total = suite.getAllMethods().size();
      // Getting the results for the said suite
      Map<String, ISuiteResult> suiteResults = suite.getResults();
      for (ISuiteResult sr : suiteResults.values()) {
        ITestContext tc = sr.getTestContext();
        // failedTests += tc.getFailedTests().getAllResults().size();
        pass = tc.getPassedTests().getAllResults().size();
        skip = tc.getSkippedTests().getAllResults().size();
        failedTests = total - pass - skip;
        System.out.println("Passed tests for suite '" + suiteName + "' is:"
                + pass);
        System.out.println("Failed tests for suite '" + suiteName + "' is:"
                + failedTests);
        System.out.println("Skipped tests for suite '" + suiteName + "' is:"
                + skip);
      }

    }

    try {
      m_out = createWriter(OUT_FOLDER);
    } catch (IOException e) {
      Reporter.log("Error relating to report output file.", true);
      e.printStackTrace();
    }
    startHtml(m_out);
    generalSuiteSummaryReport(suites);
    generateSuiteSummaryReport(suites);
    //generateMethodSummaryReport(suites);
    endHtml(m_out);
    m_out.flush();
    m_out.close();
  }

  protected PrintWriter createWriter(String OUT_FOLDER) throws IOException {
    new File(OUT_FOLDER).mkdirs();
    return new PrintWriter(new BufferedWriter(new FileWriter(new File(
            OUT_FOLDER, "CustomReport.html"))));
  }

  /**
   * Creates a table showing the highlights of each tests method with links to
   * the method details
   */
  protected void generateMethodSummaryReport(List<ISuite> suites) {
    m_methodIndex = 0;
    startResultSummaryTable("methodOverview");
    int testIndex = 1;
    for (ISuite suite : suites) {
      if (suites.size() > 1) {
        // titleRow( suite.getName(), 5 );
      }
      Map<String, ISuiteResult> r = suite.getResults();
      for (ISuiteResult r2 : r.values()) {
        ITestContext testContext = r2.getTestContext();
        String testName = testContext.getName();
        m_testIndex = testIndex;
        resultSummary(suite, testContext.getFailedTests(), testName, "failed",
                "");
        testIndex++;
      }
    }
    m_out.println("</table>");
  }

  /**
   * Creates a section showing known results and console output for each method
   */
  protected void generateMethodDetailReport(List<ISuite> suites) {
    Reporter.log("Size of suites: " + suites.size(), true);
    m_out.println("<br/><b align=\"center\">Method Summaries</b><br/>");
    m_methodIndex = 0;
    for (ISuite suite : suites) {
      m_out.println("<h1>" + suite.getName() + "</h1>");
      Map<String, ISuiteResult> r = suite.getResults();
      Reporter.log("Size of suite: " + r.size(), true);
      for (ISuiteResult r2 : r.values()) {
        ITestContext testContext = r2.getTestContext();
        if (r.values().size() > 0) {
          m_out.println("<h3>" + testContext.getName() + "</h3>");
        }
        Reporter.log("Generating method detail for failed configurations...",
                true);
        resultDetail(testContext.getFailedConfigurations());
        Reporter.log("Generating method detail for failed tests...", true);
        resultDetail(testContext.getFailedTests());
        Reporter.log("Generating method detail for skipped configurations...",
                true);
        resultDetail(testContext.getSkippedConfigurations());
        Reporter.log("Generating method detail for skipped tests...", true);
        resultDetail(testContext.getSkippedTests());
        Reporter.log("Generating method detail for passed tests...", true);
        resultDetail(testContext.getPassedTests());
      }
    }
  }

  public String convertLongToCanonicalLengthOfTime(long timeLength) {
    if (timeLength >= 86400000) {
      throw new IllegalArgumentException("Duration must be greater than zero or less than 24 hours!");
    }

    long hours = TimeUnit.MILLISECONDS.toHours(timeLength);
    timeLength -= TimeUnit.HOURS.toMillis(hours);
    long minutes = TimeUnit.MILLISECONDS.toMinutes(timeLength);
    timeLength -= TimeUnit.MINUTES.toMillis(minutes);
    long seconds = TimeUnit.MILLISECONDS.toSeconds(timeLength);

    StringBuilder sb = new StringBuilder(64);
    sb.append(hours);
    sb.append("h:");
    sb.append(minutes);
    sb.append("m:");
    sb.append(seconds);
    sb.append("s");
    System.out.println("Time passed in is: " + sb.toString());
    return (sb.toString());
  }

  /**
   *
   * @param tests
   */
  private void resultSummary(ISuite suite, IResultMap tests, String testname,
                             String style, String details) {
    if (tests.getAllResults().size() > 0) {
      StringBuffer buff = new StringBuffer();
      String lastClassName = "";
      int mq = 0;
      int cq = 0;
      String t2 = "";
      for (ITestNGMethod method : getMethodSet(tests, suite)) {
        m_row += 1;
        m_methodIndex += 1;
        ITestClass testClass = method.getTestClass();
        String className = testClass.getName();
        if (mq == 0) {
          String id = (m_testIndex == null ? null : "t"
                  + Integer.toString(m_testIndex));
          // titleRow(testname + "-" + style + details, 6, id ); // sets width
          // of 'Tests -- Passed'
          // row
          m_testIndex = null;
        }
        if (!className.equalsIgnoreCase(lastClassName)) {
          if (mq > 0) {
            cq += 1;
            m_out.print("<tr class=\"" + style + (cq % 2 == 0 ? "even" : "odd")
                    + "\">" + "<td");
            if (mq > 1) {
              m_out.print(" rowspan=\"" + mq + "\"");
            }
            m_out.println(">" + lastClassName + "</td>" + buff);
          }
          mq = 0;
          buff.setLength(0);
          lastClassName = className;
        }
        Set<ITestResult> resultSet = tests.getResults(method);
        long end = Long.MIN_VALUE;
        long start = Long.MAX_VALUE;
        for (ITestResult testResult : tests.getResults(method)) {
          if (testResult.getEndMillis() > end) {
            end = testResult.getEndMillis();
          }
          if (testResult.getStartMillis() < start) {
            start = testResult.getStartMillis();
          }
        }
        /*
         * mq += 1; if ( mq > 1 ) { buff.append("<tr class=\"" + style + (cq % 2
         * == 0 ? "odd" : "even") + "\">"); }
         */
        Date d = new Date(start);
        String formattedDate = dateFormatter.format(d);
        String description = method.getDescription();
        String t1 = qualifiedName(method);



        if (!(t2.equals(t1)) && resultSet.size() == ( 1))

        {
          t2 = t1;

          mq += 1;
          if (mq > 1) {
            buff.append("<tr class=\"" + style + (cq % 2 == 0 ? "odd" : "even")
                    + "\">");
          }
          String testInstanceName = resultSet.toArray(new ITestResult[] {})[0]
                  .getTestName();
          buff.append("<td><a href=\"#m"
                  + m_methodIndex
                  + "\">"
                  + qualifiedName(method)
                  + " "
                  + (description != null && description.length() > 0 ? "(\""
                  + description + "\")" : "")
                  + "</a>"
                  + (null == testInstanceName ? "" : "<br>(" + testInstanceName
                  + ")") + "</td>" + "<td class=\"numi\">" + resultSet.size()
                  + "</td>" + "<td>" + formattedDate + "</td>"
                  + "<td class=\"numi\">"
                  + convertLongToCanonicalLengthOfTime(end - start) + "</td>"
                  + "</tr>");
        }
      }
      if (mq > 0) {
        cq += 1;
        m_out.print("<tr class=\"" + style + (cq % 2 == 0 ? "even" : "odd")
                + "\">" + "<td");
        if (mq > 1) {
          m_out.print(" rowspan=\"" + mq + "\"");
        }
        m_out.println(">" + lastClassName + "</td>" + buff);
      }
    }
  }

  /** Starts and defines columns result summary table */
  private void startResultSummaryTable(String style) {
    tableStart(style, "summary");
    m_out
            .println("<b align=\"center\"><font color='red'>Failed Test Summary Table</font></b>");
    m_out
            .println("<tr><th>Class</th>"
                    + "<th>Method</th><th># of<br>Failed</th><th>Start</th><th>Time<br>elapsed</th></tr>");
    m_row = 0;
  }

  private String qualifiedName(ITestNGMethod method) {
    StringBuilder addon = new StringBuilder();
    String[] groups = method.getGroups();
    int length = groups.length;
    if (length > 0 && !"basic".equalsIgnoreCase(groups[0])) {
      addon.append("(");
      for (int i = 0; i < length; i++) {
        if (i > 0) {
          addon.append(", ");
        }
        addon.append(groups[i]);
      }
      addon.append(")");
    }
    return "<b>" + method.getMethodName() + "</b> " + addon;
  }

  /**
   * Called by method generateMethodDetailReport
   *
   * @param tests
   */
  private void resultDetail(IResultMap tests) {
    if (tests.size() > 0) {
      for (ITestResult result : tests.getAllResults()) {
        ITestNGMethod method = result.getMethod();
        m_methodIndex++;
        String cname = method.getTestClass().getName();
        m_out.println("<h2 id=\"m" + m_methodIndex + "\">" + cname + " : "
                + method.getMethodName() + "</h2>");
        Set<ITestResult> resultSet = tests.getResults(method);
        generateForResult(result, method, resultSet.size());
        m_out
                .println("<p class=\"totop\"><a href=\"#summary\">back to summary</a></p>");
      }
    } else {
      Reporter.log("Result map was empty.", true);
    }
  }

  /**
   * Write the first line of the stack trace
   *
   * @param tests
   */
  @SuppressWarnings("unused")
  private void getShortException(IResultMap tests) {
    for (ITestResult result : tests.getAllResults()) {
      m_methodIndex++;
      Throwable exception = result.getThrowable();
      List<String> msgs = Reporter.getOutput(result);
      boolean hasReporterOutput = msgs.size() > 0;
      boolean hasThrowable = exception != null;
      if (hasThrowable) {
        boolean wantsMinimalOutput = result.getStatus() == ITestResult.SUCCESS;
        if (hasReporterOutput) {
          m_out.print("<h3>"
                  + (wantsMinimalOutput ? "Expected Exception" : "Failure")
                  + "</h3>");
        }
        // Getting first line of the stack trace
        //String str = Utils.stackTrace(exception, true)[0];
        String str= Utils.longStackTrace(exception,true);
        scanner = new Scanner(str);
        String firstLine = scanner.nextLine();
        m_out.println(firstLine);
      }
    }
  }

  /**
   * Write all parameters
   *
   * @param tests
   */
  @SuppressWarnings("unused")
  private void getParameters(IResultMap tests) {
    for (ITestResult result : tests.getAllResults()) {
      m_methodIndex++;
      Object[] parameters = result.getParameters();
      boolean hasParameters = parameters != null && parameters.length > 0;
      if (hasParameters) {
        for (Object p : parameters) {
          m_out.println(Utils.escapeHtml(Utils.toString(p,
                  String.class)) + " | ");
        }
      }
    }
  }

  /**
   * Called by resultDetail method to show detailed information about each tests
   * including the console log.
   *
   * @param ans
   * @param method
   * @param resultSetSize
   */
  private void generateForResult(ITestResult ans, ITestNGMethod method,
                                 int resultSetSize) {
    Object[] parameters = ans.getParameters();
    boolean hasParameters = parameters != null && parameters.length > 0;
    tableStart("result", null);
    if (hasParameters) {

      m_out.print("<tr class=\"param\">");
      for (int x = 1; x <= parameters.length; x++) {
        m_out.print("<th>Param." + x + "</th>");
      }
      m_out.println("</tr>");
      m_out.print("<tr class=\"param stripe\">");
      for (Object p : parameters) {
        m_out.println("<td>"
                + Utils.escapeHtml(Utils.toString(p, String.class)) + "</td>");
      }
      m_out.println("</tr>");
    } else {
      m_out.println("<tr><td><i>Test did not have parameters.</i></td></tr> ");
    }
    List<String> msgs = Reporter.getOutput(ans);
    boolean hasReporterOutput = msgs.size() > 0;
    Throwable exception = ans.getThrowable();
    boolean hasThrowable = exception != null;
    if (hasReporterOutput || hasThrowable) {
      if (hasParameters) {
        m_out.print("<tr><td");
        if (parameters.length > 1) {
          m_out.print(" colspan=\"" + parameters.length + "\"");
        }
        m_out.println(">");
      } else {
        m_out.println("<div>");
      }
      if (hasReporterOutput) {
        if (hasThrowable) {
          m_out.println("<h3>Test Messages</h3>");
        }
        for (String line : msgs) {
          m_out.println(line + "<br>");
        }
      }
      if (hasThrowable) {
        boolean wantsMinimalOutput = ans.getStatus() == ITestResult.SUCCESS;
        if (hasReporterOutput) {
          m_out.println("<h3>"
                  + (wantsMinimalOutput ? "Expected Exception" : "Failure")
                  + "</h3>");
        }
        generateExceptionReport(exception, method);
      }
      if (hasParameters) {
        m_out.println("</td></tr>");
      } else {
        m_out.println("</div>");
      }
    } else {
      m_out
              .println("<tr><td><i>Test did not have report output.</i></td></tr>");
    }
    m_out.println("</table>");
  }

  protected void generateExceptionReport(Throwable exception,
                                         ITestNGMethod method) {
    m_out.print("<div class=\"stacktrace\">");
    m_out.print(Utils.longStackTrace(exception,true));
    m_out.println("</div>");
  }

  /**
   * Since the methods will be sorted chronologically, we want to return the
   * ITestNGMethod from the invoked methods.
   */
  private Collection<ITestNGMethod> getMethodSet(IResultMap tests, ISuite suite) {
    List<IInvokedMethod> r = Lists.newArrayList();
    List<IInvokedMethod> invokedMethods = suite.getAllInvokedMethods();
    for (IInvokedMethod im : invokedMethods) {
      if (tests.getAllMethods().contains(im.getTestMethod())) {
        r.add(im);
      }
    }
    Arrays.sort(r.toArray(new IInvokedMethod[r.size()]), new TestSorter());
    List<ITestNGMethod> result = Lists.newArrayList();
    // Add all the invoked methods
    for (IInvokedMethod m : r) {
      result.add(m.getTestMethod());
    }
    // Add all the methods that weren't invoked (e.g. skipped) that we
    // haven't added yet
    for (ITestNGMethod m : tests.getAllMethods()) {
      if (!result.contains(m)) {
        result.add(m);
      }
    }
    return result;
  }

  public void generalSuiteSummaryReport(List<ISuite> suites) {
    // printExecutionParameters();
    m_out.println("<b align=\"center\">Summary</b>");
    tableStart("resultOverview", null);
    m_out.print("<tr>");
    tableColumnStart("Total Tests");
    tableColumnStart("Total Passed");
    tableColumnStart("Total Failed");
    tableColumnStart("Total skipped");

    m_out.println("</tr>");

    int qty_tests = 0;
    int qty_pass_s = 0;
    int qty_skip = 0;
    int qty_fail = 0;

    m_testIndex = 1;
    int failedTests = 0;
    int totalTest=0;

    if (suites.size() == 0)
      throw new TestNGException(
              "You need to have at lease one suite to generate a report.");
    for (ISuite suite : suites) {
      // titleRow( suite.getName(), 8 );
      totalTest = suite.getAllMethods().size();
      Map<String, ISuiteResult> tests = suite.getResults();
      for (ISuiteResult r : tests.values()) {
        qty_tests += 1;
        ITestContext overview = r.getTestContext();
        // startSummaryRow( overview.getName() );
        int q = getMethodSet(overview.getPassedTests(), suite).size();
        // qty_pass_m += q;
        // summaryCell( q, Integer.MAX_VALUE );
        q = overview.getPassedTests().size();
        qty_pass_s += q;
        // summaryCell( q, Integer.MAX_VALUE );
        q = getMethodSet(overview.getSkippedTests(), suite).size();
        q = overview.getSkippedTests().size();
        qty_skip += q;
        // summaryCell(q, 0);
        q = getMethodSet(overview.getFailedTests(), suite).size();
        q = overview.getFailedTests().size();
        qty_fail += q;

        failedTests = qty_fail;
        if (failedTests < 0)
          failedTests = 0;

        int totalExecuted = qty_pass_s + qty_skip + qty_fail;
        totalTest=totalExecuted;

        // summaryCell(q, 0);

        // summaryCell( overview.getIncludedGroups() );
        // summaryCell( overview.getExcludedGroups() );
        m_out.println("</tr>");
        m_testIndex++;
      }
      // m_out.println("<tr class=\"total\"><td>Total</td>");
      // summaryCell( qty_pass_m, Integer.MAX_VALUE );

    }
    summaryCell(totalTest, 0);
    summaryCell(qty_pass_s, Integer.MAX_VALUE);
    summaryCell(failedTests, 0);
    summaryCell(qty_skip, 0);


    // String passedTime = convertLongToCanonicalLengthOfTime( testEnd -
    // testStart );
    // summaryCell( "9999", true); //TODO fix this
    // m_out.println("<td colspan=\"2\">&nbsp;</td></tr>");
    m_out.println("</table>");
    m_out.println("<p></p>");
    m_out.println("<!--FAILED_TESTS:" + failedTests + ":-->");
    int failedAndSkippedTests = failedTests + qty_skip;
    m_out.println("<!--FAILED_AND_SKIPPED_TESTS:" + failedAndSkippedTests
            + ":-->");

  }

  public void generateSuiteSummaryReport(List<ISuite> suites) {
    // printExecutionParameters();
    m_out.println("<b align=\"center\">Execution Summary</b>");
    tableStart("testOverview", null);
    m_out.print("<tr>");
    tableColumnStart("Test");
    // tableColumnStart("Methods<br>Passed");
    tableColumnStart("Scenarios<br>Passed");
    tableColumnStart("# skipped");
    tableColumnStart("#failed<br>");
    tableColumnStart("Total<br>Time");
    // tableColumnStart("Included<br>Groups");
    // tableColumnStart("Excluded<br>Groups");
    m_out.println("</tr>");

    int qty_tests = 0;
    int qty_pass_m = 0;
    int qty_pass_s = 0;
    int qty_skip = 0;
    int qty_fail = 0;
    int failedTests = 0;


    long testStart;
    long testEnd;
    m_testIndex = 1;
    if (suites.size() == 0)
      throw new TestNGException(
              "You need to have at lease one suite to generate a report.");
    for (ISuite suite : suites) {
      // titleRow( suite.getName(), 8 );
      int TotalTests = suite.getAllMethods().size();
      Map<String, ISuiteResult> tests = suite.getResults();
      for (ISuiteResult r : tests.values()) {
        qty_tests += 1;
        ITestContext overview = r.getTestContext();
        startSummaryRow(overview.getName());
        int q = getMethodSet(overview.getPassedTests(), suite).size();
        qty_pass_m += q;
        // summaryCell( q, Integer.MAX_VALUE );
        q = overview.getPassedTests().size();
        qty_pass_s += q;
        summaryCell(q, Integer.MAX_VALUE);
        q = getMethodSet(overview.getSkippedTests(), suite).size();
        q = overview.getSkippedTests().size();
        qty_skip += q;
        summaryCell(q, 0);
        q = getMethodSet(overview.getFailedTests(), suite).size();
        q = overview.getFailedTests().size();
        qty_fail += q;
        summaryCell(q, 0);
        testStart = overview.getStartDate().getTime();
        testEnd = overview.getEndDate().getTime();
        String passedTime = convertLongToCanonicalLengthOfTime(testEnd
                - testStart);
        summaryCell(passedTime, true);
        // summaryCell( overview.getIncludedGroups() );
        // summaryCell( overview.getExcludedGroups() );
        m_out.println("</tr>");
        m_testIndex++;
      }

    }

    m_out.println("<tr class=\"total\"/><td>Total</td>");
    // summaryCell( qty_pass_m, Integer.MAX_VALUE );
    if (qty_fail < 0)
      qty_fail = 0;
    summaryCell(qty_pass_s, Integer.MAX_VALUE);
    summaryCell(qty_skip, 0);
    summaryCell(qty_fail, 0);
    // String passedTime = convertLongToCanonicalLengthOfTime( testEnd -
    // testStart );
    summaryCell("", true); // TODO fix this
    // m_out.println("<td colspan=\"2\">&nbsp;</td></tr>");
    m_out.println("</table>");
    m_out.println("<p></p>");
  }

  private void printExecutionParameters() {
    m_out.println("<b>Execution Parameters</b>");
    tableStart("testOverview", null);
    m_out.print("<tr>");
    tableColumnStart("baseUrl");
    tableColumnStart("hubUrl");
    tableColumnStart("osType");
    tableColumnStart("browser");
    tableColumnStart("browserVersion");
    tableColumnStart("resolution");
    tableColumnStart("variable");
    m_out.println("</tr>");
    summaryCell("baseUrl", true);
    summaryCell("hubUrl", true);
    summaryCell("osType", true);
    summaryCell("browser", true);
    summaryCell("browserVersion", true);
    summaryCell("resolution", true);
    summaryCell("variable", true);
    m_out.println("</table>");
    m_out.println("<p></p>");
  }

  private void summaryCell(String[] val) {
    StringBuffer b = new StringBuffer();
    for (String v : val) {
      b.append(v + " ");
    }
    summaryCell(b.toString(), true);
  }

  private void summaryCell(String v, boolean isgood) {
    m_out.print("<td class=\"numi" + (isgood ? "" : "_attn") + "\">" + v
            + "</td>");
  }

  private void startSummaryRow(String label) {
    m_row += 1;
    m_out.print("<tr" + (m_row % 2 == 0 ? " class=\"stripe\"" : "")
            + "><td style=\"text-align:left;padding-right:2em\"><a href=\"#t"
            + m_testIndex + "\">" + label + "</a>" + "</td>");
  }

  private void summaryCell(int v, int maxexpected) {
    summaryCell(String.valueOf(v), v <= maxexpected);
  }

  private void tableStart(String cssclass, String id) {
    m_out.println("<table cellspacing=\"0\" cellpadding=\"0\""
            + (cssclass != null ? " class=\"" + cssclass + "\""
            : " style=\"padding-bottom:2em\"")
            + (id != null ? " id=\"" + id + "\"" : "") + ">");
    m_row = 0;
  }

  private void tableColumnStart(String label) {
    m_out.print("<th>" + label + "</th>");
  }

  private void titleRow(String label, int cq) {
    titleRow(label, cq, null);
  }

  private void titleRow(String label, int cq, String id) {
    m_out.print("<tr");
    if (id != null) {
      m_out.print(" id=\"" + id + "\"");
    }
    m_out.println("><th colspan=\"" + cq + "\">" + label + "</th></tr>");
    m_row = 0;
  }

  /** Starts HTML stream */
  protected void startHtml(PrintWriter out) {
    out.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" \"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">");
    out.println("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
    out.println("<head>");
    out.println("<title>BANC Selenium RC Execution Report</title>");
    out.println("<style type=\"text/css\">");
    out.println("table {margin-bottom:10px;border-collapse:collapse;empty-cells:show}");
    out.println("td,th {border:1px solid #009;padding:.25em .5em}");
    out.println(".result th {vertical-align:bottom}");
    out.println(".param th {padding-left:1em;padding-right:1em}");
    out.println(".param td {padding-left:.5em;padding-right:2em}");
    out.println(".stripe td,.stripe th {background-color: #E6EBF9}");
    out.println(".numi,.numi_attn {text-align:right}");
    out.println(".total td {font-weight:bold}");
    out.println(".passedodd td {background-color: #0A0}");
    out.println(".passedeven td {background-color: #3F3}");
    out.println(".skippedodd td {background-color: #CCC}");
    out.println(".skippedodd td {background-color: #DDD}");
    out.println(".failedodd td,.numi_attn {background-color: #F33}");
    out.println(".failedeven td,.stripe .numi_attn {background-color: #D00}");
    out.println(".stacktrace {white-space:pre;font-family:monospace}");
    out.println(".totop {font-size:85%;text-align:center;border-bottom:2px solid #000}");
    out.println("</style>");
    out.println("</head>");
    out.println("<body>");
  }

  /** Finishes HTML stream */
  protected void endHtml(PrintWriter out) {
    out.println("<center><h4>Customized TestNG Report</h4></center>");
    out.println("</body></html>");
  }

  // ~ Inner Classes --------------------------------------------------------
  /** Arranges methods by classname and method name */
  private class TestSorter implements Comparator<IInvokedMethod> {
    /** Arranges methods by classname and method name */
    @Override
    public int compare(IInvokedMethod o1, IInvokedMethod o2) {
      return (int) (o1.getDate() - o2.getDate());
    }

  }
}
