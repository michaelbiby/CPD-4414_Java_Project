/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WordServlet;

import generator.word_rand;
import database.credentials;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author c0644696
 */
@WebServlet("/WordServlet")
public class WordServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

        response.setHeader("Content-Type", "text/plain-text");
        try (PrintWriter out = response.getWriter()) {

            word_rand wr = new word_rand();

            int num = wr.random();
            System.out.println("random: " + num);
            String word = getResults("SELECT word FROM words WHERE serial_no = ?", String.valueOf(num));
            String hidden = wr.newWord(word);
            System.out.println(hidden);

        } catch (IOException ex) {
            System.out.println("Exception: " + e.getMessage());
            //Logger.getLogger(WordServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Provides POST /WordServlet?name=XXX&age=XXX
     *
     * @param request - the request object
     * @param response - the response object
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        Set<String> keySet = request.getParameterMap().keySet();
        try (PrintWriter out = response.getWriter()) {
            if (keySet.contains("serial_no") && keySet.contains("word")) {
                // There are some parameters     
                String num = request.getParameter("serial_no");
                String word = request.getParameter("word");

                doUpdate("INSERT INTO words (serial_no,word) VALUES (?, ?)", num, word);
            } else {
                // There are no parameters at all
                out.println("Error: Not enough data to input. Please use a URL of the form");
            }
        } catch (IOException ex) {
            Logger.getLogger(WordServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) {
        Set<String> keySet = request.getParameterMap().keySet();
        try (PrintWriter out = response.getWriter()) {
            if (keySet.contains("serial_no") && keySet.contains("word")) {
                // There are some parameters  
                String num = request.getParameter("serial_no");
                String word = request.getParameter("word");

                doUpdate("UPDATE words SET serial_no = ?, word = ? WHERE serial_no = ?", num, word);
            } else {
                // There are no parameters at all
                out.println("Error: Not enough data to input. Please use a URL");
            }
        } catch (IOException ex) {
            Logger.getLogger(WordServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
        Set<String> keySet = request.getParameterMap().keySet();
        try (PrintWriter out = response.getWriter()) {
            if (keySet.contains("serial_no")) {
                // There are some parameters                
                String num = request.getParameter("serial_no");

                doUpdate("DELETE FROM words WHERE serial_no = ?", num);
            } else {
                // There are no parameters at all
                out.println("Error: Not enough data to input. Please use a URL");
            }
        } catch (IOException ex) {
            Logger.getLogger(WordServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String getResults(String query, String... params) {
        StringBuilder sb = new StringBuilder();
        String word = "";
        try {
            Connection conn = credentials.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query);
            for (int i = 1; i <= params.length; i++) {
                pstmt.setString(i, params[i - 1]);
            }
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {

                word = rs.getString("word");
            }
        } catch (SQLException ex) {
            Logger.getLogger(WordServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return word;

    }

    private int doUpdate(String query, String... params) {
        int numChanges = 0;
        try (Connection conn = credentials.getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(query);
            for (int i = 1; i <= params.length; i++) {
                pstmt.setString(i, params[i - 1]);
            }
            numChanges = pstmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(WordServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return numChanges;
    }
}
