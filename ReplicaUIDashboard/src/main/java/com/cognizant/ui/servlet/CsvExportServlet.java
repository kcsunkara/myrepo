package com.cognizant.ui.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.cognizant.ui.dao.DashboardDAOImpl;

/**
 * Servlet implementation class CsvExportServlet
 */
public class CsvExportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(CsvExportServlet.class);


    public void doGet (HttpServletRequest   req, HttpServletResponse  res)
        throws ServletException, IOException {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Calendar cal= Calendar.getInstance();
        LOG.debug(dateFormat.format(cal.getTime()));
        String name=dateFormat.format(cal.getTime());
                res.setContentType("text/csv"); 
                res.setHeader("Content-Disposition", "attachment; filename="+name+".csv"); // Force "Save As" dialogue.
                
        
                PrintWriter fw = res.getWriter();
                 Cookie[] cookies = null;
                 cookies = req.getCookies();
                 String csvData="";
                 String[] cookiesStrings;
                 
                 if( cookies != null ){
                     for (Cookie cookie : cookies){
                        if(cookie.getName().equalsIgnoreCase("csvData"))
                        {
                            csvData= cookie.getValue();
                            cookiesStrings=csvData.split("&");
                            for(String cookString:cookiesStrings)
                              {
                               LOG.info("CSV Data IS::" +cookString);
                                fw.write(cookString);
                                fw.write("\n");
                               }
                        }
                        
                     }
                 }
                 
                LOG.info("getting data from session: "+ csvData);
                 
                LOG.info("After getwriter");
                 fw.flush();
                 fw.close();
    }

}
