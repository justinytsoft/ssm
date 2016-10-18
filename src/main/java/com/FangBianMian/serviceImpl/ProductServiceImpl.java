package com.FangBianMian.serviceImpl;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.FangBianMian.dao.IProductDao;
import com.FangBianMian.domain.Product;
import com.FangBianMian.domain.ProductCategory;
import com.FangBianMian.domain.ProductComment;
import com.FangBianMian.domain.ProductImg;
import com.FangBianMian.service.IProductService;
import com.FangBianMian.utils.DataUtil;
import com.FangBianMian.utils.SettingUtil;

@Service
public class ProductServiceImpl implements IProductService {

	@Autowired
	private IProductDao productDao;

	@Override
	public void deleteProductById(Integer id) {
		productDao.deleteProductById(id);
	}
	
	@Override
	public Product queryProductById(Integer id, Boolean status) {
		return productDao.queryProductById(id, status);
	}

	@Override
	public List<Product> queryProductsByParam(Map<String, Object> param) {
		return productDao.queryProductsByParam(param);
	}

	@Override
	public int queryProductsByParamTotal(Map<String, Object> param) {
		return productDao.queryProductsByParamTotal(param);
	}

	@Override
	public void saveProduct(Product p, String[] productImgs) {
		
		try{
			//将图片移到正式目录
			for(int i = 0; i < productImgs.length; i++){
				String img = productImgs[i];
				String tempPath = SettingUtil.getCommonSetting("upload.file.temp.path") + File.separator + img;// 临时文件夹
				File file = new File(tempPath);
				if(file.exists() && file.isFile()){ //判断是否存在临时目录，存在则移到正式目录并返回路径
					productImgs[i] = DataUtil.moveToDir(img, true);
				}
			}
			
			//设置商品默认展示图
			p.setDefault_img(productImgs[0]);

			if(p.getId()!=null){
				//更新商品信息
				productDao.updateProduct(p); 
				//查询商品的图片
				List<ProductImg> pis = productDao.selectProductImgByPid(p.getId());
				//删除商品图片
				productDao.deleteProductImgs(p.getId());
				
				//删除正式目录下的图片
				for(int i = 0; i < pis.size(); i++){
					ProductImg pi = pis.get(i); //旧图片
					String newPath = productImgs[i]; //新图片
					if(!pi.getImg().equals(newPath)){ //如果旧图和新图不一样表示修改了图片，这时就需要删除旧图
						DataUtil.deleteByUploadImg(pi.getImg());
					}
				}
			}else{
				//保存商品信息
				productDao.insertProduct(p);
			}
			
			//保存商品图片
			productDao.insertProductImgs(p.getId(), productImgs);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public List<ProductComment> queryProductComments(Map<String, Object> param) {
		return productDao.queryProductComments(param);
	}

	@Override
	public int queryProductCommentsTotal(Map<String, Object> param) {
		return productDao.queryProductCommentsTotal(param);
	}
	
	@Override
	public ProductCategory queryProductCategoryById(Integer category_id) {
		return productDao.queryProductCategoryById(category_id);
	}
	
	@Override
	public void insertProductComment(ProductComment pc) {
		productDao.insertProductComment(pc);
	}
}
