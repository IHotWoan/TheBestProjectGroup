# TheBestProjectGroup
Welcome to visit our repository of TechPhive webshop.

This project is a webshop web-application intended to run on WildFLy(JBoss) with Java EE 7.
The web application is composed of two modes: admin, and customer mode. All pages for admin mode can be found in ./WebContent/admin/ folder, and all the rest will be found in ./WebContent folder.

The web-application stores images/orders/products/ and other information in an external database.
Therefore, it needs to link MySQL database to the deployed project.

Our project uses WildFly's datasource to manage database connection.
In order to project to work please configure your location of database on your WildFly, and JNDI should be "java:/MySqlDS".

We also offer e-mail confirmation of order and notification of order status changes. In order to have e-mail function to work, revisit your WildFly configuration and set a e-mail information as JNDI "java:/shopmail", and configure properly your standalone.xml file inside your JBOSS(WildFly) directory.

If you have further questions, please feel free to contact us.

This project is a part of project assignment in Linnaeus University, Sweden.


sl222xk[a-t]student.lnu.se
