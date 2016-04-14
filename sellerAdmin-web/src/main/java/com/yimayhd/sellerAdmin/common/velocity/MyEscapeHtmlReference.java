package com.yimayhd.sellerAdmin.common.velocity;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.oro.text.perl.MalformedPerl5PatternException;
import org.apache.oro.text.perl.Perl5Util;
import org.apache.velocity.app.event.implement.EscapeReference;
import org.apache.velocity.runtime.RuntimeServices;
import org.apache.velocity.util.StringUtils;

/**
 * 彻底重写EscapeReference<br>
 * 以支持velocity notmatch属性
 * 
 * @author yebin
 *
 */
public class MyEscapeHtmlReference extends EscapeReference {
	private Perl5Util		perl;
	private RuntimeServices	rs;
	private String			notMatchRegExp;

	public MyEscapeHtmlReference() {
		perl = new Perl5Util();
		notMatchRegExp = null;
	}

	@Override
	public Object referenceInsert(String reference, Object value) {
		if (value == null)
			return value;
		if (notMatchRegExp == null)
			return escape(value);
		if (!perl.match(notMatchRegExp, reference))
			return escape(value);
		else
			return value;
	}

	@Override
	protected String getMatchAttribute() {
		return "eventhandler.escape.html.notmatch";
	}

	@Override
	protected String escape(Object text) {
		return StringEscapeUtils.escapeHtml(text.toString());
	}

	public void setRuntimeServices(RuntimeServices rs) {
		this.rs = rs;
		notMatchRegExp = StringUtils.nullTrim(rs.getConfiguration().getString(getMatchAttribute()));
		if (notMatchRegExp != null && notMatchRegExp.length() == 0)
			notMatchRegExp = null;
		if (notMatchRegExp != null)
			try {
				perl.match(notMatchRegExp, "");
			} catch (MalformedPerl5PatternException E) {
				rs.getLog().error(
						"Invalid regular expression '" + notMatchRegExp + "'.  No escaping will be performed.", E);
				notMatchRegExp = null;
			}
	}

	@Override
	protected RuntimeServices getRuntimeServices() {
		return rs;
	}

}
