package paypal;

/**
 * Paypal Button and Instant Payment Notification (IPN) Integration with Java
 * http://codeoftheday.blogspot.com/2013/07/paypal-button-and-instant-payment_6.html
 */

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.Mensajeria;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


@WebServlet("/IpnHandler")
public class IpnHandler extends HttpServlet
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	
	private Logger logger;
    private IpnConfig ipnConfig;
    private IpnInfoService ipnInfoService;

    /**
     * This method handles the Paypal IPN Notification as follows:
     *      1. Read all posted request parameters
     *      2. Prepare 'notify-validate' command with exactly the same parameters
     *      3. Post above command to Paypal IPN URL {@link IpnConfig#ipnUrl}
     *      4. Read response from Paypal
     *      5. Capture Paypal IPN information
     *      6. Validate captured Paypal IPN Information
     *          6.1. Check that paymentStatus=Completed
     *          6.2. Check that txnId has not been previously processed
     *          6.3. Check that receiverEmail matches with configured {@link IpnConfig#receiverEmail}
     *          6.4. Check that paymentAmount matches with configured {@link IpnConfig#paymentAmount}
     *          6.5. Check that paymentCurrency matches with configured {@link IpnConfig#paymentCurrency}
     *      7. In case of any failed validation checks, throw {@link IpnException}
     *      8. If all is well, return {@link IpnInfo} to the caller for further business logic execution
     *
     * @param request {@link HttpServletRequest}
     * @return {@link IpnInfo}
     * @throws IpnException
     */
    public IpnInfo handleIpn (HttpServletRequest request) throws IpnException {
    	try {
    		new Mensajeria().enviarCorreo("bryan.braz@gmail.com", "Paypal", "handleIpn");
		} catch (Exception e) {
			new Mensajeria().enviarCorreo("bryan.braz@gmail.com", "Paypal", e.getMessage());
		}
    	new Mensajeria().enviarCorreo("bryan.braz@gmail.com", "Paypal", "Inp 0");
    	//logger = new Logger("algo");
        //logger.info("inside ipn");
        new Mensajeria().enviarCorreo("bryan.braz@gmail.com", "Paypal", "Inp 1");
        IpnInfo ipnInfo = new IpnInfo();
        new Mensajeria().enviarCorreo("bryan.braz@gmail.com", "Paypal", "Inp 2");
        try
        {
            //1. Read all posted request parameters
            String requestParams = this.getAllRequestParams(request);
            new Mensajeria().enviarCorreo("bryan.braz@gmail.com", "Paypal", "Inp 3");
            //logger.info(requestParams);
            new Mensajeria().enviarCorreo("bryan.braz@gmail.com", "Paypal", "Inp 4");

            //2. Prepare 'notify-validate' command with exactly the same parameters
            Enumeration en = request.getParameterNames();
            new Mensajeria().enviarCorreo("bryan.braz@gmail.com", "Paypal", "Inp 5");
            StringBuilder cmd = new StringBuilder("cmd=_notify-validate");
            new Mensajeria().enviarCorreo("bryan.braz@gmail.com", "Paypal", "Inp 6");
            String paramName;
            new Mensajeria().enviarCorreo("bryan.braz@gmail.com", "Paypal", "Inp 7");
            String paramValue;
            new Mensajeria().enviarCorreo("bryan.braz@gmail.com", "Paypal", "Inp 8");
            while (en.hasMoreElements()) {
            	new Mensajeria().enviarCorreo("bryan.braz@gmail.com", "Paypal", "Inp 9");
                paramName = (String) en.nextElement();
                new Mensajeria().enviarCorreo("bryan.braz@gmail.com", "Paypal", "Inp 10");
                paramValue = request.getParameter(paramName);
                new Mensajeria().enviarCorreo("bryan.braz@gmail.com", "Paypal", "Inp 11");
                cmd.append("&").append(paramName).append("=")
                        .append(URLEncoder.encode(paramValue, request.getParameter("charset")));
                new Mensajeria().enviarCorreo("bryan.braz@gmail.com", "Paypal", "Inp 12");
            }
            
            new Mensajeria().enviarCorreo("bryan.braz@gmail.com", "Paypal", "Inp 13");
            //3. Post above command to Paypal IPN URL {@link IpnConfig#ipnUrl}
            URL u = new URL(this.getIpnConfig().getIpnUrl());
            new Mensajeria().enviarCorreo("bryan.braz@gmail.com", "Paypal", "Inp 14");
            HttpsURLConnection uc = (HttpsURLConnection) u.openConnection();
            new Mensajeria().enviarCorreo("bryan.braz@gmail.com", "Paypal", "Inp 15");
            uc.setDoOutput(true);
            new Mensajeria().enviarCorreo("bryan.braz@gmail.com", "Paypal", "Inp 16");
            uc.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            new Mensajeria().enviarCorreo("bryan.braz@gmail.com", "Paypal", "Inp 17");
            uc.setRequestProperty("Host", "www.paypal.com");
            new Mensajeria().enviarCorreo("bryan.braz@gmail.com", "Paypal", "Inp 18");
            PrintWriter pw = new PrintWriter(uc.getOutputStream());
            new Mensajeria().enviarCorreo("bryan.braz@gmail.com", "Paypal", "Inp 19");
            pw.println(cmd.toString());
            new Mensajeria().enviarCorreo("bryan.braz@gmail.com", "Paypal", "Inp 20");
            pw.close();
            new Mensajeria().enviarCorreo("bryan.braz@gmail.com", "Paypal", "Inp 21");

            //4. Read response from Paypal
            BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
            new Mensajeria().enviarCorreo("bryan.braz@gmail.com", "Paypal", "Inp 22");
            String res = in.readLine();
            new Mensajeria().enviarCorreo("bryan.braz@gmail.com", "Paypal", "Inp 23");
            in.close();
            new Mensajeria().enviarCorreo("bryan.braz@gmail.com", "Paypal", "Inp 24");

            //5. Capture Paypal IPN information
            ipnInfo.setLogTime(System.currentTimeMillis());
            new Mensajeria().enviarCorreo("bryan.braz@gmail.com", "Paypal", "Inp 25");
            ipnInfo.setItemName(request.getParameter("item_name"));
            new Mensajeria().enviarCorreo("bryan.braz@gmail.com", "Paypal", "Inp 26");
            ipnInfo.setItemNumber(request.getParameter("item_number"));
            new Mensajeria().enviarCorreo("bryan.braz@gmail.com", "Paypal", "Inp 27");
            ipnInfo.setPaymentStatus(request.getParameter("payment_status"));
            new Mensajeria().enviarCorreo("bryan.braz@gmail.com", "Paypal", "Inp 28");
            ipnInfo.setPaymentAmount(request.getParameter("mc_gross"));
            new Mensajeria().enviarCorreo("bryan.braz@gmail.com", "Paypal", "Inp 29");
            ipnInfo.setPaymentCurrency(request.getParameter("mc_currency"));
            new Mensajeria().enviarCorreo("bryan.braz@gmail.com", "Paypal", "Inp 30");
            ipnInfo.setTxnId(request.getParameter("txn_id"));
            new Mensajeria().enviarCorreo("bryan.braz@gmail.com", "Paypal", "Inp 31");
            ipnInfo.setReceiverEmail(request.getParameter("receiver_email"));
            new Mensajeria().enviarCorreo("bryan.braz@gmail.com", "Paypal", "Inp 32");
            ipnInfo.setPayerEmail(request.getParameter("payer_email"));
            new Mensajeria().enviarCorreo("bryan.braz@gmail.com", "Paypal", "Inp 33");
            ipnInfo.setResponse(res);
            new Mensajeria().enviarCorreo("bryan.braz@gmail.com", "Paypal", "Inp 34");
            ipnInfo.setRequestParams(requestParams);
            new Mensajeria().enviarCorreo("bryan.braz@gmail.com", "Paypal", "Inp 35");

            //6. Validate captured Paypal IPN Information
            if (res.equals("VERIFIED")) {
            	new Mensajeria().enviarCorreo("bryan.braz@gmail.com", "Paypal", "Inp 36");

                //6.1. Check that paymentStatus=Completed
                if(ipnInfo.getPaymentStatus() == null || !ipnInfo.getPaymentStatus().equalsIgnoreCase("COMPLETED"))
                    ipnInfo.setError("payment_status IS NOT COMPLETED {" + ipnInfo.getPaymentStatus() + "}");
                new Mensajeria().enviarCorreo("bryan.braz@gmail.com", "Paypal", "Inp 37");
                //6.2. Check that txnId has not been previously processed
                IpnInfo oldIpnInfo = this.getIpnInfoService().getIpnInfo(ipnInfo.getTxnId());
                if(oldIpnInfo != null)
                    ipnInfo.setError("txn_id is already processed {old ipn_info " + oldIpnInfo);
                new Mensajeria().enviarCorreo("bryan.braz@gmail.com", "Paypal", "Inp 38");
                //6.3. Check that receiverEmail matches with configured {@link IpnConfig#receiverEmail}
                if(!ipnInfo.getReceiverEmail().equalsIgnoreCase(this.getIpnConfig().getReceiverEmail()))
                    ipnInfo.setError("receiver_email " + ipnInfo.getReceiverEmail()
                            + " does not match with configured ipn email " + this.getIpnConfig().getReceiverEmail());
                new Mensajeria().enviarCorreo("bryan.braz@gmail.com", "Paypal", "Inp 39");
                //6.4. Check that paymentAmount matches with configured {@link IpnConfig#paymentAmount}
                if(Double.parseDouble(ipnInfo.getPaymentAmount()) != Double.parseDouble(this.getIpnConfig().getPaymentAmount()))
                    ipnInfo.setError("payment amount mc_gross " + ipnInfo.getPaymentAmount()
                            + " does not match with configured ipn amount " + this.getIpnConfig().getPaymentAmount());
                new Mensajeria().enviarCorreo("bryan.braz@gmail.com", "Paypal", "Inp 40");
                //6.5. Check that paymentCurrency matches with configured {@link IpnConfig#paymentCurrency}
                if(!ipnInfo.getPaymentCurrency().equalsIgnoreCase(this.getIpnConfig().getPaymentCurrency()))
                    ipnInfo.setError("payment currency mc_currency " + ipnInfo.getPaymentCurrency()
                            + " does not match with configured ipn currency " + this.getIpnConfig().getPaymentCurrency());
                new Mensajeria().enviarCorreo("bryan.braz@gmail.com", "Paypal", "Inp 41");
            }
            else{
            	new Mensajeria().enviarCorreo("bryan.braz@gmail.com", "Paypal", "Inp 42");
            	ipnInfo.setError("Inavlid response {" + res + "} expecting {VERIFIED}");
            	new Mensajeria().enviarCorreo("bryan.braz@gmail.com", "Paypal", "Inp 43");
            }
            new Mensajeria().enviarCorreo("bryan.braz@gmail.com", "Paypal", "Inp 44");
            //logger.info("ipnInfo = " + ipnInfo);
            new Mensajeria().enviarCorreo("bryan.braz@gmail.com", "Paypal", "Inp 45");
            this.getIpnInfoService().log(ipnInfo);
            new Mensajeria().enviarCorreo("bryan.braz@gmail.com", "Paypal", "Inp 46");
            //7. In case of any failed validation checks, throw {@link IpnException}
            if(ipnInfo.getError() != null){
            	new Mensajeria().enviarCorreo("bryan.braz@gmail.com", "Paypal", "Inp 47");
            	throw new IpnException(ipnInfo.getError());
            }
            new Mensajeria().enviarCorreo("bryan.braz@gmail.com", "Paypal", "Inp 48");
        }
        catch(Exception e)
        {
        	new Mensajeria().enviarCorreo("bryan.braz@gmail.com", "Paypal", "Inp 49");
            if(e instanceof IpnException){
            	new Mensajeria().enviarCorreo("bryan.braz@gmail.com", "Paypal", "Inp 50");
            	throw (IpnException) e;
            }
            new Mensajeria().enviarCorreo("bryan.braz@gmail.com", "Paypal", "Inp 51");
            //logger.log(Level.SEVERE, e.toString(), e);
            new Mensajeria().enviarCorreo("bryan.braz@gmail.com", "Paypal", "Inp 52");
            throw new IpnException(e.toString());
        }
        new Mensajeria().enviarCorreo("bryan.braz@gmail.com", "Paypal", "Inp 53");
        //8. If all is well, return {@link IpnInfo} to the caller for further business logic execution
        return ipnInfo;
    }

    /**
     * Utility method to extract all request parameters and their values from request object
     *
     * @param request {@link HttpServletRequest}
     * @return all request parameters in the form:
     *                                              param-name 1
     *                                                  param-value
     *                                              param-name 2
     *                                                  param-value
     *                                                  param-value (in case of multiple values)
     */
    private String getAllRequestParams(HttpServletRequest request)
    {
    	try {
    		new Mensajeria().enviarCorreo("bryan.braz@gmail.com", "Paypal", "getAllRequestParams");
		} catch (Exception e) {
			// TODO: handle exception
		}
        Map map = request.getParameterMap();
        StringBuilder sb = new StringBuilder("\nREQUEST PARAMETERS\n");
        for (Iterator it = map.keySet().iterator(); it.hasNext();)
        {
            String pn = (String)it.next();
            sb.append(pn).append("\n");
            String[] pvs = (String[]) map.get(pn);
            for (int i = 0; i < pvs.length; i++) {
                String pv = pvs[i];
                sb.append("\t").append(pv).append("\n");
            }
        }
        return sb.toString();
    }

    public Logger getLogger() {
        return logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public IpnConfig getIpnConfig() {
        return ipnConfig;
    }

    public void setIpnConfig(IpnConfig ipnConfig) {
        this.ipnConfig = ipnConfig;
    }

    public IpnInfoService getIpnInfoService() {
        return ipnInfoService;
    }

    public void setIpnInfoService(IpnInfoService ipnInfoService) {
        this.ipnInfoService = ipnInfoService;
    }

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
    		new Mensajeria().enviarCorreo("bryan.braz@gmail.com", "Paypal", "doGet");
    		IpnInfo ipn = handleIpn(req);
    		new Mensajeria().enviarCorreo("bryan.braz@gmail.com", "Paypal", ipn.toString());
    		
		} catch (Exception e) {
			new Mensajeria().enviarCorreo("bryan.braz@gmail.com", "Paypal", e.getMessage() + "Error doGet");
		}
	}
/*
	@Override
	protected long getLastModified(HttpServletRequest req) {
		try {
    		new Mensajeria().enviarCorreo("bryan.braz@gmail.com", "Paypal", "getLastModified");
		} catch (Exception e) {
			// TODO: handle exception
		}
		return 0;
	}
*/
	@Override
	protected void doHead(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
    		new Mensajeria().enviarCorreo("bryan.braz@gmail.com", "Paypal", "doHead");
    		IpnInfo ipn = handleIpn(req);
    		new Mensajeria().enviarCorreo("bryan.braz@gmail.com", "Paypal", ipn.toString());
    		
		} catch (Exception e) {
			new Mensajeria().enviarCorreo("bryan.braz@gmail.com", "Paypal", e.getMessage());
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
    		new Mensajeria().enviarCorreo("bryan.braz@gmail.com", "Paypal", "doPost");
    		IpnInfo ipn = handleIpn(req);
    		new Mensajeria().enviarCorreo("bryan.braz@gmail.com", "Paypal", ipn.toString());
    		
		} catch (Exception e) {
			new Mensajeria().enviarCorreo("bryan.braz@gmail.com", "Paypal", e.getMessage());
		}
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
    		new Mensajeria().enviarCorreo("bryan.braz@gmail.com", "Paypal", "doPut");
    		IpnInfo ipn = handleIpn(req);
    		new Mensajeria().enviarCorreo("bryan.braz@gmail.com", "Paypal", ipn.toString());
    		
		} catch (Exception e) {
			new Mensajeria().enviarCorreo("bryan.braz@gmail.com", "Paypal", e.getMessage() + "Error doPut");
		}
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
    		new Mensajeria().enviarCorreo("bryan.braz@gmail.com", "Paypal", "doDelete");
    		IpnInfo ipn = handleIpn(req);
    		new Mensajeria().enviarCorreo("bryan.braz@gmail.com", "Paypal", ipn.toString());
    		
		} catch (Exception e) {
			new Mensajeria().enviarCorreo("bryan.braz@gmail.com", "Paypal", e.getMessage() + "Error doDelete");
		}
	}

	@Override
	protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
    		new Mensajeria().enviarCorreo("bryan.braz@gmail.com", "Paypal", "doOptions");
    		IpnInfo ipn = handleIpn(req);
    		new Mensajeria().enviarCorreo("bryan.braz@gmail.com", "Paypal", ipn.toString());
    		
		} catch (Exception e) {
			new Mensajeria().enviarCorreo("bryan.braz@gmail.com", "Paypal", e.getMessage() + "Error doOptions");
		}
	}

	@Override
	protected void doTrace(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
    		new Mensajeria().enviarCorreo("bryan.braz@gmail.com", "Paypal", "doTrace");
    		IpnInfo ipn = handleIpn(req);
    		new Mensajeria().enviarCorreo("bryan.braz@gmail.com", "Paypal", ipn.toString());
    		
		} catch (Exception e) {
			new Mensajeria().enviarCorreo("bryan.braz@gmail.com", "Paypal", e.getMessage() + "Error doTrace");
		}
	}
/*
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
    		new Mensajeria().enviarCorreo("bryan.braz@gmail.com", "Paypal", "service1");
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
*/
	/*@Override
	public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
		try {
    		new Mensajeria().enviarCorreo("bryan.braz@gmail.com", "Paypal", "service2");
    		//tx=5HR20496EN457990F&st=Completed&amt=1000.00&cc=USD&cm=&item_number=
    		
    		
		} catch (Exception e) {
			// TODO: handle exception
		}
	}*/
    
    
}