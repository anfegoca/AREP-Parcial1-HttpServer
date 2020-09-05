package edu.eci.arep.HttpServer;

import spark.Request;
import spark.Response;
import static spark.Spark.*;

import edu.eci.arep.HttpServer.model.Calculator;

public class SparkWebApp {

    public static void main(String[] args) {
        port(getPort());
        get("/inputdata", (req, res) -> inputDataPage(req, res));
        get("/results", (req, res) -> resultsPage(req, res));
    }

    private static String inputDataPage(Request req, Response res) {
        
        String pageContent = "<!DOCTYPE html>" + "<html>" + "<body>" + "<h2>Mean and Standad Desviation</h2>"
                + "<p>Insert the numbers separated by commas(,) then press 'submit' to calculate the Mean and Standad Desviation </p>"
                + "<form action=\"/results\">" + "  Data:<br>" + "  <input type=\"text\" name=\"number\" value=\"\">"
                + " <input type=\"text\" name=\"oper\" value=\"\">" + "  <br><br>"
                + "  <input type=\"submit\" value=\"Submit\">" + "</form>" + "</body>" + "</html>";
        return pageContent;
    }

    private static String resultsPage(Request req, Response res) {

        String oper = req.queryParams("oper");
        Double num = Double.parseDouble(req.queryParams("number"));
        Double respuesta=0.0;
        
        switch (oper) {
            case "sin":
                respuesta = Calculator.getInstance().operate(num, Calculator.getInstance().sin);
                break;
            case "cos":
                respuesta = Calculator.getInstance().operate(num, Calculator.getInstance().cos);
                break;
            case "tan":
                respuesta = Calculator.getInstance().operate(num, Calculator.getInstance().tan);
                break;
        }
        String json = "{"+oper+":"+respuesta+"}";

        return json;
    }

    static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567; // returns default port if heroku-port isn't set (i.e. on localhost)
    }

}
