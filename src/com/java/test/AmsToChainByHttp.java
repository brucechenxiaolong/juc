package com.cmcc.mss.chain;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * 档案系统上链接口
 * @author zhangsx
 *
 */
public class AmsToChainByHttp {



	/**
	 * 数据上链
	 * @param address 上链地址
	 * @param jsonData
	 * @param accessKey
	 * @return
	 */
	public static String setEvidence(String address,String jsonData,String accessKey){
		return doPostReq(address, jsonData, accessKey);
	}

	/**
	 * 获取历史上链记录 分页查询全宗id下数据
	 * @param address
	 * @param accessKey
	 * @return
	 */
	public static String getEvidences(String address,String accessKey){
		return doGetReq(address, accessKey);
	}

	/**
	 * 验证
	 * @param address
	 * @param jsonData
	 * @param accessKey
	 * @return
	 */
	public static String verifyEvidenceInfo(String address,String jsonData,String accessKey){
		return doPostReq(address, jsonData, accessKey);
	}



	/**
	 * 发送get请求
	 * @param address
	 * @param accessKey
	 * @return
	 */
	public static String doGetReq(String address,String accessKey){
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(address);
		CloseableHttpResponse response = null;
		try {
			httpGet.setHeader("Content-Type", "application/json;charset=UTF-8");
//			httpGet.setHeader("accessKey", accessKey);

			response = httpclient.execute(httpGet);
			StatusLine status = response.getStatusLine();
			int state = status.getStatusCode();
			if (state == HttpStatus.SC_OK) {
				HttpEntity responseEntity = response.getEntity();
				String jsonString = EntityUtils.toString(responseEntity);
				return jsonString;
			} else{
				System.out.println("请求出错："+state+"");
			}
		} catch (Exception ex) {
			System.out.println("error:" + ex.getMessage());
			ex.printStackTrace();
			return "";

		}finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
					return "";
				}
			}
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
				return "";
			}
		}
		return "";
	}

	/**
	 * 发送post请求
	 * @param address
	 * @param jsonData
	 * @param accessKey
	 * @return
	 */
	public static String doPostReq(String address,String jsonData,String accessKey){
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(address);
		CloseableHttpResponse response = null;
		try {
			httpPost.setHeader("Content-Type", "application/json;charset=UTF-8");
			httpPost.setHeader("accessKey", accessKey);

			String charSet = "UTF-8";
			StringEntity entity = new StringEntity(jsonData, charSet);
			httpPost.setEntity(entity);

			response = httpclient.execute(httpPost);
			StatusLine status = response.getStatusLine();
			int state = status.getStatusCode();
			if (state == HttpStatus.SC_OK) {
				HttpEntity responseEntity = response.getEntity();
				String jsonString = EntityUtils.toString(responseEntity);
				return jsonString;
			} else{
				System.out.println("请求出错："+state+"");
			}
		} catch (Exception ex) {
			System.out.println("error:" + ex.getMessage());
			ex.printStackTrace();
			return "";

		}finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
					return "";
				}
			}
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
				return "";
			}
		}
		return "";
	}

	public static void main(String[] args) {
		System.out.println(doGetReq("http://58.23.149.211:9009/Liems/webservice/getBfLogList?date=2021-11-08","d6a374aa-50a1-4c28-8a44-1ec89acaecf2"));
	}


}
