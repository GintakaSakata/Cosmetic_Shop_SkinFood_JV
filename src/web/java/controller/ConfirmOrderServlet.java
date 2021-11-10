package web.java.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import web.java.dao.CouponDAO;
import web.java.dao.Order;
import web.java.dao.TransportDAO;
import web.java.model.Cart;
import web.java.model.CartItem;

/**
 * Servlet implementation class ConfirmOrderServlet
 */
@WebServlet("/confirmOrder")
public class ConfirmOrderServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ConfirmOrderServlet() {
	super();
	// TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	// TODO Auto-generated method stub
	response.getWriter().append("Served at: ").append(request.getContextPath());
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	// TODO Auto-generated method stub
	response.setContentType("text/html;charset=UTF-8");
	request.setCharacterEncoding("utf-8");
	String orderName = request.getParameter("orderName");
	String orderAddress = request.getParameter("orderAddress");
	String orderPhone = request.getParameter("orderPhone");
	String transport = request.getParameter("hidden_transport");
	String magiamgia = request.getParameter("hidden_coupon");
	String orderNote = request.getParameter("orderNote");
	String total = request.getParameter("hidden_total");
	String userId = request.getParameter("userLogin");
	Order order = new Order();
	
	
	Double transportFee = new TransportDAO().getTransportFeeById(transport);
	Double discountAmount = new CouponDAO().getCouponDiscountAmount(magiamgia);
	Double totalDeal = Double.parseDouble(total);
	Double totalOrder = (totalDeal + transportFee)*(100-discountAmount)/100;
	System.out.println(totalDeal);
	System.out.println(transportFee);
	System.out.println(discountAmount);
	System.out.println(totalOrder);
	
	ArrayList<CartItem> cartItems = ((Cart) request.getSession(false).getAttribute("cart")).getCartItems();
	
	order.addOrderTotal(cartItems,orderName,orderAddress,orderPhone,transport,magiamgia,orderNote,totalOrder,userId);
	
	HttpSession session = request.getSession();
	session.removeAttribute("cart");
	
	response.sendRedirect("history");
    }

}
