/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import database.credentials;
import generator.word_rand;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 *
 * @author c0644696
 */
@Path("/word")
public class Words {

    @GET
    @Produces("application/json")
    public String doGet() throws SQLException {
        int num = word_rand.random();
        String name = getResults("SELECT word FROM words WHERE serial_no = ?", String.valueOf(num));
        return "{\"name\" : \""+name+"\"}";
    }

    private String getResults(String query, String... params) throws SQLException {
        String word = "";
        try (Connection conn = credentials.getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(query);
            if (params.length != 0) {
                pstmt.setString(1, params[0]);
            }
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                word = rs.getString("word");
            }
        }
        return word;
    }

}
