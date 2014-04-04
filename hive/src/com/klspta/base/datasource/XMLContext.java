package com.klspta.base.datasource;

public class XMLContext {
    public static final String head1 = 
    	new StringBuffer("<?xml version=\"1.0\" encoding=\"GB2312\"?>")
        .append(" \n <beans xmlns=\"http://www.springframework.org/schema/beans\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:context=\"http://www.springframework.org/schema/context\" xmlns:tx=\"http://www.springframework.org/schema/tx\"")
        .append(" \n xmlns:jdbc=\"http://www.springframework.org/schema/jdbc\" xmlns:p=\"http://www.springframework.org/schema/p\" ")
        .append(" \n xsi:schemaLocation=\"http://www.springframework.org/schema/beans   ")
        .append(" \n http://www.springframework.org/schema/beans/spring-beans-3.0.xsd   ")
        .append(" \n http://www.springframework.org/schema/context   ")
        .append(" \n http://www.springframework.org/schema/context/spring-context-3.0.xsd")   
        .append(" \n http://www.springframework.org/schema/tx   ")
        .append(" \n http://www.springframework.org/schema/tx/spring-tx-3.0.xsd")   
        .append(" \n http://www.springframework.org/schema/jdbc   ")
        .append(" \n http://www.springframework.org/schema/jdbc/spring-jdbc-3.0.xsd\">")
        .append(" \n <bean class=\"org.springframework.beans.factory.config.PropertyPlaceholderConfigurer\">")
        .append(" \n    <property name=\"locations\">")
        .append(" \n         <value>file:#JDBCFILEPATHlocaldatabase.properties</value>")
        .append(" \n    </property>")
        .append(" \n </bean>").toString();
    public static final String head2 = " \n </beans>";
    public static final String bean1 = 
    	new StringBuffer(" \n <bean id=\"#Name\" class=\"org.springframework.jdbc.datasource.DriverManagerDataSource\" lazy-init=\"false\" scope=\"singleton\" >")
        .append(" \n <property name=\"driverClassName\" value=\"${#Name.jdbc.driverClassName}\" />")
        .append(" \n <property name=\"url\" value=\"${#Name.jdbc.url}\" />")
        .append(" \n <property name=\"username\" value=\"${#Name.jdbc.username}\" />")
        .append(" \n <property name=\"password\" value=\"${#Name.jdbc.password}\" />")
        .append(" \n </bean>").toString();
    
    public static final String bean2 = 
    	new StringBuffer(" \n <bean id=\"#NameTemplate\" class=\"org.springframework.jdbc.core.JdbcTemplate\" lazy-init=\"false\" scope=\"singleton\">")
        .append(" \n <property name=\"dataSource\">")
        .append(" \n    <ref bean=\"#Name\" />")
        .append(" \n </property>")
        .append(" \n </bean>").toString();
    
    public static final String jdbc = 
    	new StringBuffer("#Name.jdbc.driverClassName=#ClassName")
        .append("\n#Name.jdbc.url=#url")
        .append("\n#Name.jdbc.username=#username")
        .append("\n#Name.jdbc.password=#password\n").toString();
    
    public static final String workflow = 
    	new StringBuffer(" \n <bean id=\"springHelper\" class=\"org.jbpm.pvm.internal.processengine.SpringHelper\" >")
        .append("\n <property name=\"jbpmCfg\" value=\"com/klspta/base/workflow/conf/jbpm.cfg.xml\"></property>")
        .append("\n </bean>")
        .append("\n <bean id=\"processEngine\" factory-bean=\"springHelper\" factory-method=\"createProcessEngine\" />")
        .append("\n <bean id=\"sessionFactory\" class=\"org.springframework.orm.hibernate3.LocalSessionFactoryBean\">")
        .append("\n <property name=\"dataSource\" ref=\"WORKFLOW\" />")
        .append("\n <property name=\"mappingResources\">")
        .append("\n <list>")
        .append("\n <value>jbpm.repository.hbm.xml</value>")
        .append("\n <value>jbpm.execution.hbm.xml</value>")
        .append("\n <value>jbpm.history.hbm.xml</value>")
        .append("\n <value>jbpm.task.hbm.xml</value>")
        .append("\n <value>jbpm.identity.hbm.xml</value>")
        .append("\n </list>")
        .append("\n </property>")
        .append("\n <property name=\"hibernateProperties\">")
        .append("\n   <props>")
        .append("\n  <prop key=\"hibernate.dialect\">org.hibernate.dialect.OracleDialect</prop>")
        .append("\n  <prop key=\"hibernate.hbm2ddl.auto\">update</prop>")
        .append("\n   </props>")
        .append("\n </property>")
        .append("\n  </bean>")
        .append("\n <bean id=\"transactionManager\" class=\"org.springframework.orm.hibernate3.HibernateTransactionManager\">")
        .append("\n   <property name=\"sessionFactory\" ref=\"sessionFactory\" />")
        .append("\n   <property name=\"dataSource\" ref=\"WORKFLOW\" />")
        .append("\n  </bean>").toString();
}
