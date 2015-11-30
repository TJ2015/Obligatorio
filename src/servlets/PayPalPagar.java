package servlets;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;

import dominio.datatypes.DataUsuario;
import negocio.interfases.IControladorUsuario;
import util.Mensajeria;
import util.PayPalUtil;


@WebServlet("/PayPalPay")
public class PayPalPagar extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	private IControladorUsuario cUsuario;
	
    public PayPalPagar() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String aprovado = request.getParameter("approved");
		
		if( aprovado != null ) {
			if( aprovado.equals("true")) {
				APIContext apiContext = PayPalUtil.prepararCliente();
				String payerId = request.getParameter("PayerID");
				String paymentId = request.getParameter("paymentId");
				
				try {
					// Get the PayPal payment
					Payment payment = Payment.get(apiContext, paymentId);
					PaymentExecution execution = new PaymentExecution();
					execution.setPayerId(payerId);
					
					// Execute PayPal payment (charge)
					payment.execute(apiContext, execution);

					try {
						HttpSession session = request.getSession();
						String nickUsuario = (String)session.getAttribute("nickname");
						DataUsuario dataUsuario = cUsuario.agregarUsuarioPremiun(nickUsuario);
						session.setAttribute("dataUsuario", dataUsuario);
						String mensaje = obtenerMensajePayPal(dataUsuario);
						Mensajeria enviar = new Mensajeria();
						enviar.enviarCorreo(dataUsuario.getEmail(), "SAPo - Felicitaciones!!!", mensaje);
						response.sendRedirect("/Obligatorio/venta.xhtml");
					} catch (Exception e) {
						System.out.println("No se guarda la venta");
						response.getWriter().append("Compra cancelada.");
					}
				} catch (PayPalRESTException e) {
					e.printStackTrace();
					response.getWriter().append("Compra cancelada.");
				}
			} else {		
				response.getWriter().append("Compra cancelada.");
			}
		}
	}
	
	private String obtenerMensajePayPal(DataUsuario dataUsuario){
		return "<p>Estimado " + dataUsuario.getNombre() + " " + dataUsuario.getApellido() + ":</p>"
				+ "<br><p>La compra realizada por PayPal ha sido finalizada con exito.</p>"
				+ "<p>Eres nuestro nuevo usuario PREMIUM!!!</p>"
				+ "<p>Podras crear AV ilimitados.</p><br>"
				+ "<p>Esperamos que disfrutes de nuestros servicios</p><br>"
				+ "<p>Saludos</p>"
				+ "<p>El equipo de SAPo</p>";
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
