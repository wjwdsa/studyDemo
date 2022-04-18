package com.dlyd.application.gbm.service.domain;

import java.io.Serializable;


/**
 * 
 * SYS_XTCS
 * @author yangbing
 * @date 2020-07-17 13:26:08
 */
public class SysXtcsDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	*$column.comments
	*/
		private String xtbz;
	/**
	*$column.comments
	*/
		private String xtmc;
	/**
	*$column.comments
	*/
		private String xtjc;
	/**
	*$column.comments
	*/
		private String xtrq;
	/**
	*$column.comments
	*/
		private Integer lsh;
	/**
	*$column.comments
	*/
		private Integer xtzt;

	/**
	 * 设置：${column.comments}
	 */
	public void setXtbz(String xtbz) {
				this.xtbz = xtbz == null ? null : xtbz .trim();
			}
	/**
	 * 获取：${column.comments}
	 */
	public String getXtbz() {
				        		return xtbz == null ? null : xtbz .trim();
        			}
	/**
	 * 设置：${column.comments}
	 */
	public void setXtmc(String xtmc) {
				this.xtmc = xtmc == null ? null : xtmc .trim();
			}
	/**
	 * 获取：${column.comments}
	 */
	public String getXtmc() {
				        		return xtmc == null ? null : xtmc .trim();
        			}
	/**
	 * 设置：${column.comments}
	 */
	public void setXtjc(String xtjc) {
				this.xtjc = xtjc == null ? null : xtjc .trim();
			}
	/**
	 * 获取：${column.comments}
	 */
	public String getXtjc() {
				        		return xtjc == null ? null : xtjc .trim();
        			}
	/**
	 * 设置：${column.comments}
	 */
	public void setXtrq(String xtrq) {
				this.xtrq = xtrq;
			}
	/**
	 * 获取：${column.comments}
	 */
	public String getXtrq() {
				        			return xtrq;
        			}
	/**
	 * 设置：${column.comments}
	 */
	public void setLsh(Integer lsh) {
				this.lsh = lsh;
			}
	/**
	 * 获取：${column.comments}
	 */
	public Integer getLsh() {
				        			return lsh;
        			}
	/**
	 * 设置：${column.comments}
	 */
	public void setXtzt(Integer xtzt) {
				this.xtzt = xtzt;
			}
	/**
	 * 获取：${column.comments}
	 */
	public Integer getXtzt() {
				        			return xtzt;
        			}

 @Override
  public String toString() {
    return "SysXtcsDO{" +
        	 "xtbz='" + xtbz + '\'' +
        	 "xtmc='" + xtmc + '\'' +
        	 "xtjc='" + xtjc + '\'' +
        	 "xtrq='" + xtrq + '\'' +
        	 "lsh='" + lsh + '\'' +
        	 "xtzt='" + xtzt + '\'' +
                '}';
  }

}
