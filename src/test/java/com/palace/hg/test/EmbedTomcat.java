package com.palace.hg.test;

import java.io.File;
import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;
import org.apache.tomcat.util.scan.StandardJarScanFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmbedTomcat {

    private static final Logger LOG = LoggerFactory.getLogger(EmbedTomcat.class);

   
    public static void main(String[] args) throws Exception {
    	startTomcatAA();
    }
    public static void startTomcatAA() throws Exception {
    	Tomcat tom = new Tomcat();
    	tom.setPort(8080);
    	Context context = tom.addWebapp("", new File("src/main/webapp/").getAbsolutePath());
    	StandardRoot resource = new StandardRoot(context);
    	resource.addPreResources(new DirResourceSet(resource, "/WEB-INF/classes",new File("target/classes").getAbsolutePath(), "/"));
    	context.setResources(resource);
		StandardJarScanFilter filter = new StandardJarScanFilter();
		String jars = getJars();
		System.out.println(jars);
		filter.setPluggabilitySkip(jars);
		filter.setTldSkip(jars);
		context.getJarScanner().setJarScanFilter(filter);
    	tom.start();
    	Thread.currentThread().sleep(500*60*1000);
    }
	
	public static String getJars() {
		StringBuilder sb =new StringBuilder();
		for(String ss : getJarSet()) {
			sb.append(ss).append(",");
		}
		return sb.deleteCharAt(sb.length()-1).toString();
	}
	static Set<String> getJarSet() {
		Set<String> patterns = new LinkedHashSet<>();
		patterns.add("xerces-J_1.4.0*.jar");
		patterns.add("commons-logging-1.1*.jar");
		patterns.add("ant-*.jar");
		patterns.add("aspectj*.jar");
		patterns.add("commons-beanutils*.jar");
		patterns.add("commons-codec*.jar");
		patterns.add("commons-collections*.jar");
		patterns.add("commons-dbcp*.jar");
		patterns.add("commons-digester*.jar");
		patterns.add("commons-fileupload*.jar");
		patterns.add("commons-httpclient*.jar");
		patterns.add("commons-io*.jar");
		patterns.add("commons-lang*.jar");
		patterns.add("commons-logging*.jar");
		patterns.add("commons-math*.jar");
		patterns.add("commons-pool*.jar");
		patterns.add("geronimo-spec-jaxrpc*.jar");
		patterns.add("h2*.jar");
		patterns.add("hamcrest*.jar");
		patterns.add("hibernate*.jar");
		patterns.add("jmx*.jar");
		patterns.add("jmx-tools-*.jar");
		patterns.add("jta*.jar");
		patterns.add("junit-*.jar");
		patterns.add("httpclient*.jar");
		patterns.add("log4j-*.jar");
		patterns.add("mail*.jar");
		patterns.add("org.hamcrest*.jar");
		patterns.add("slf4j*.jar");
		patterns.add("tomcat-embed-core-*.jar");
		patterns.add("tomcat-embed-logging-*.jar");
		patterns.add("tomcat-jdbc-*.jar");
		patterns.add("tomcat-juli-*.jar");
		patterns.add("tools.jar");
		patterns.add("wsdl4j*.jar");
		patterns.add("xercesImpl-*.jar");
		patterns.add("xmlParserAPIs-*.jar");
		patterns.add("xml-apis-*.jar");
		patterns.add("antlr-*.jar");
		patterns.add("aopalliance-*.jar");
		patterns.add("aspectjrt-*.jar");
		patterns.add("aspectjweaver-*.jar");
		patterns.add("classmate-*.jar");
		patterns.add("dom4j-*.jar");
		patterns.add("ecj-*.jar");
		patterns.add("ehcache-core-*.jar");
		patterns.add("hibernate-core-*.jar");
		patterns.add("hibernate-commons-annotations-*.jar");
		patterns.add("hibernate-entitymanager-*.jar");
		patterns.add("hibernate-jpa-2.1-api-*.jar");
		patterns.add("hibernate-validator-*.jar");
		patterns.add("hsqldb-*.jar");
		patterns.add("jackson-annotations-*.jar");
		patterns.add("jackson-core-*.jar");
		patterns.add("jackson-databind-*.jar");
		patterns.add("jandex-*.jar");
		patterns.add("javassist-*.jar");
		patterns.add("jboss-logging-*.jar");
		patterns.add("jboss-transaction-api_*.jar");
		patterns.add("jcl-over-slf4j-*.jar");
		patterns.add("jdom-*.jar");
		patterns.add("jul-to-slf4j-*.jar");
		patterns.add("log4j-over-slf4j-*.jar");
		patterns.add("logback-classic-*.jar");
		patterns.add("logback-core-*.jar");
		patterns.add("rome-*.jar");
		patterns.add("slf4j-api-*.jar");
		patterns.add("spring-aop-*.jar");
		patterns.add("spring-aspects-*.jar");
		patterns.add("spring-beans-*.jar");
		patterns.add("spring-boot-*.jar");
		patterns.add("spring-core-*.jar");
		patterns.add("spring-context-*.jar");
		patterns.add("spring-data-*.jar");
		patterns.add("spring-expression-*.jar");
		patterns.add("spring-jdbc-*.jar,");
		patterns.add("spring-orm-*.jar");
		patterns.add("spring-oxm-*.jar");
		patterns.add("spring-tx-*.jar");
		patterns.add("snakeyaml-*.jar");
		patterns.add("tomcat-embed-el-*.jar");
		patterns.add("validation-api-*.jar");
		patterns.add("xml-apis-*.jar");
		patterns.add("cglib-nodep-*.jar");
		patterns.add("xml-apis-*.jar");
		patterns.add("druid-*.jar");
		
		return patterns;
	}
}
