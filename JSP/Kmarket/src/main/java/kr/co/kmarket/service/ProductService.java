package kr.co.kmarket.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import kr.co.kmarket.dao.ProductDAO;
import kr.co.kmarket.vo.CartVO;
import kr.co.kmarket.vo.CateVO;
import kr.co.kmarket.vo.OrderVO;
import kr.co.kmarket.vo.ProductVO;


public enum ProductService {

	INSTANCE;
	private ProductDAO dao;
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private ProductService() {
		dao = new ProductDAO();
	}
	
	public void insertProduct(ProductVO vo) {
		logger.info("ProductService...insertProduct...");
		dao.insertProduct(vo);
	}
	
	public List<ProductVO> selectProducts(String prodCate1, String prodCate2) {
		logger.info("ProductService...selectProducts...");
		return dao.selectProducts(prodCate1, prodCate2);
	}
	public List<CateVO> selectProdCate1() {
		logger.info("ProductService...selectProdCate1...");
		return dao.selectProdCate1();
	}
	public List<CateVO> selectProdCate2(String prodCate1) {
		logger.info("ProductService...selectProdCate2...");
		return dao.selectProdCate2(prodCate1);
	}
	public CateVO selectProdCates(String prodCate1, String prodCate2) {
		logger.info("ProductService...selectProdCates...");
		return dao.selectProdCates(prodCate1, prodCate2);
	}
	public ProductVO selectProduct(String prodNo) {
		logger.info("ProductService...selectProduct...");
		return dao.selectProduct(prodNo);
	}
	public CartVO selectProductForOrder(String prodNo) {
		logger.info("ProductService...selectProductForOrder...");
		return dao.selectProductForOrder(prodNo);
	}
	public int selectCountTotal(String prodCate1, String prodCate2) {
		return dao.selectCountTotal(prodCate1, prodCate2);
	}
	public List<ProductVO> selectOrderByHighsold(String prodCate1, String prodCate2) {
		return dao.selectOrderByHighsold(prodCate1, prodCate2);
	}
	public List<ProductVO> selectOrderByLowPrice(String prodCate1, String prodCate2) {
		return dao.selectOrderByLowPrice(prodCate1, prodCate2);
	}
	public List<ProductVO> selectOrderByHighPrice(String prodCate1, String prodCate2) {
		return dao.selectOrderByHighPrice(prodCate1, prodCate2);
	}
	public List<ProductVO> selectOrderByHighRating(String prodCate1, String prodCate2) {
		return dao.selectOrderByHighRating(prodCate1, prodCate2);
	}
	public List<ProductVO> selectOrderByHighReview(String prodCate1, String prodCate2) {
		return dao.selectOrderByHighReview(prodCate1, prodCate2);
	}
	public List<ProductVO> selectOrderByNewold(String prodCate1, String prodCate2) {
		return dao.selectOrderByNewold(prodCate1, prodCate2);
	}
	// adminproducts
	public List<ProductVO> selectadminproducts(){
		return dao.selectadminProducts();
	}
	public int selectadminCountTotal() {
		return dao.selectadminCountTotal();
	}
	public List<ProductVO> searchadminproducts(String search,String search2){
		return dao.searchadminproducts(search,search2);
	}
	// cart 
	public int insertCart(CartVO vo) {
		logger.info("ProductService...insertCart...");
		return dao.insertCart(vo);
	}
	public List<CartVO> selectCarts(String uid) {
		logger.info("ProductService...selectCarts...");
		return dao.selectCarts(uid);
	}
	public CartVO selectCart(String cartNo) {
		logger.info("ProductService...selectCart...");
		return dao.selectCart(cartNo);
	}
	public int deleteCartList(String uid, String cartNo) {
		logger.info("ProductService...deleteCartList...");
		return dao.deleteCartList(uid, cartNo);
	}
	public void deleteCartList(String uid, int cartNo) {
		logger.info("ProductService...deleteCartList...");
		dao.deleteCartList(uid, cartNo);
	}
	
	// order
	public void insertOrder(OrderVO vo) {
		logger.info("ProductService...insertOrder...");
		dao.insertOrder(vo);
	}
	public void insertOrderItem(CartVO cart, String ordNo) {
		logger.info("ProductService...inserOrderItem...");
		dao.insertOrderItem(cart, ordNo);
	}
	public void insertMemberPoint(String uid, String ordNo, String point) {
		logger.info("ProductService...insertMemberPoint...");
		dao.insertMemberPoint(uid, ordNo, point);
	}
	public int selectLatestOrder(String uid) {
		logger.info("ProductService...selectOrderNo...");
		return dao.selectLatestOrder(uid);
	}
	public OrderVO selectOrder(String uid) {
		logger.info("ProductService...selectOrder...");
		return dao.selectOrder(uid);
	}
	public int selectOrderNo(String uid) {
		logger.info("ProductService...selectOrderNo...");
		return dao.selectOrderNo(uid);
	}
	public List<ProductVO> selectOrderProducts(String prodNo) {
		logger.info("ProductService...selectOrderProducts...");
		return dao.selectOrderProducts(prodNo);
	}
	public int selectMemberPoint(String uid) {
		logger.info("ProductService...selectMemberPoint...");
		return dao.selectMemberPoint(uid);
	}
	public void updateMemberPoint(int totalsavepoint, int totalusedpoint, String uid) {
		logger.info("ProductService...updateSaveMemberPoint...");
		dao.updateMemberPoint(totalsavepoint, totalusedpoint, uid);
	}
	
	
	// 서비스 로직
	public String getSavePath(HttpServletRequest req) {
		ServletContext application = req.getServletContext();
		return application.getRealPath("/file");
	}
	
	public MultipartRequest uploadFile(HttpServletRequest req, String savePath) throws IOException {
		int maxSize = 1024 *1024 * 10;
		return new MultipartRequest(req, savePath, maxSize, "UTF-8", new DefaultFileRenamePolicy());
	}
	
	public String renameFile(String fname, String savePath) {
		
		int i = fname.lastIndexOf(".");
		String ext = fname.substring(i);
		
		String name = UUID.randomUUID().toString();
		String newName = name+ext;
		
		File f1 = new File(savePath+"/"+fname);
		File f2 = new File(savePath+"/"+newName);
		
		f1.renameTo(f2);
		
		return newName;
		
	}
	
	public int getLastPageNum(int total) {
		int lastPageNum = 0;
		
		if(total % 10 == 0) {
			lastPageNum = (total/10);
		}else {
			lastPageNum = (total/10)+1;
		}
		return lastPageNum;
		
	}
	
	public int getCurrentPage(String pg) {
		int currentPage = 1;
		
		if(pg != null) {
			currentPage = Integer.parseInt(pg);
		}
		return currentPage;
	}
	
	public int getLimitStart(int currentPage) {
		int limitStart = 0;
		limitStart = (currentPage-1)*10;
		return limitStart;
	}
	
	public int[] getPageGroupNum(int currentPage, int lastPageNum) {
		int pageGroupCurrent = (int)Math.ceil(currentPage/10.0);
		int pageGroupStart = (pageGroupCurrent - 1) * 10 + 1;
		int pageGroupEnd = pageGroupCurrent * 10;
		
		if(pageGroupEnd > lastPageNum) {
			pageGroupEnd = lastPageNum;
		}
		
		int[] result = {pageGroupStart, pageGroupEnd};
		return result;
	}
	
	public int getPageStartNum(int total, int limitStart) {
		logger.debug("getPageStartNum...");
		int pageStartNum = 0;
		
		pageStartNum = total - limitStart;
		
		return pageStartNum;
	}
	
	public int getStartNum(int currentPage) {
		return (currentPage - 1)*10;
	}

	
}
