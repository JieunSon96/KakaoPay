<img alt="Java" src ="https://img.shields.io/badge/Java-007396.svg?&style=for-the-badge&logo=Java&logoColor=white"/> <img alt="Spring Boot" src ="https://img.shields.io/badge/Spring Boot-6DB33F.svg?&style=for-the-badge&logo=Spring Boot&logoColor=white"/> <img alt="PostgreSQL" src ="https://img.shields.io/badge/PostgreSQL-4169E1.svg?&style=for-the-badge&logo=PostgreSQL&logoColor=white"/> <img alt="Swagger" src ="https://img.shields.io/badge/Swagger-85EA2D.svg?&style=for-the-badge&logo=Swagger&logoColor=white"/> 

# Coupon System
This ia a Functional Backend Stack Coupon System. This is targeting large-scale services. It is built using Java, PostgreSQL, and Spring Boot framework.


# Development Stack
* Programming Language : Java 14
* Database :  PostgreSQL
* Framework : Spring Boot 2.3.0
    * Gradle
    * JPA
    * Junit 5
* IDE : IntelliJ IDEA Ultimate
* OS : Windows
* Version Control : Git

# Build and Execution
   * Terminal Environment
   <pre><code>
   $ ./gradlew clean build
   $ java -jar kakao-0.0.1-SNAPSHOT.jar
   </code></pre>
   
   * Access URI 
   <pre><code>http://localhost:5000</code></pre>

   * Swagger
   <pre><code>http://localhost:5000/swagger-ui.html</code></pre>

# API List

### 1. Coupon Generation API
  <pre>
  URL : /api/coupon/createCoupon
  Method : POST</pre>
  
 * Header
 
  |Token|Authentication Authority|
|------|-----|
|Bearer|ADMIN|


###  2. Coupon Payment API
   <pre>
  URL : /api/coupon/payCoupon
  Method : POST</pre>
  
   * Header
   
 |Token|Authentication Authority|
 |-----|------------------------|
|Bearer|     ADMIN,USER         |

   * Parameter
   
  |Value|Type|Required|
|------|---|---|
|email|String|Y|

###  3. Coupon Retrieval API
   <pre>
  URL : /api/coupon/viewCoupon
  Method : GET</pre>
  
  * Header
   
 |Token|Authentication Authority|
|------|---|
|Bearer |ADMIN,USER|

   * Parameter
   
  |Value|Type|Required|
|------|---|---|
|email|String|Y|


###  4. Coupon Utilization API   
<pre>
  URL : /api/coupon/useCoupon/{couponId}/{utilizationStatus}
  Method : GET</pre>
  
  * Header
   
 |Token|Authentication Authority|
|------|---|
|Bearer |USER|

   * Parameter
   
  |Value|Type|Required|
|------|---|---|
|email|String|Y|
|couponId|Long|Y|

### 5. Coupon Cancellation API
<pre>
  URL : /api/coupon/useCoupon/{couponId}/{utilizationStatus}
  Method : GET</pre>
  
  * Header
   
 |Token|Authentication Authority|
|------|---|
|Bearer |USER|

   * Parameter
   
  |Value|Type|Required|
|------|---|---|
|email|String|Y|
|couponId|Long|Y|

###  6. Expiration Coupons Retrieval API
<pre>
  URL : /api/coupon/viewExpirationCoupons
  Method : GET</pre>
  
  * Header
   
 |Token|Authentication Authority|
|------|---|
|Bearer |ADMIN|

###  7. User Registration API 
 * Spring Security
 * a token-based api authentication function using jwt
 
 <pre>
 URL : /api/user/signup
 Method : POST</pre>

   * Parameter
   
  |Value|Type|Required|
|------|---|---|
|email|String|Y|
|name|String|Y|
|password|String|Y|   
   
   
###  8. User Login API 
   <pre>
  URL : /api/user/signin
  Method : POST</pre>

  * Parameter
   
  |Value|Type|Required|
|------|---|---|
|email|String|Y|
|password|String|Y|


## + Message Sending function before 3 days expiration of issued Coupon
   <pre> 
   * getAllCouponsByExpirationDate() : the scheduler to check coupons 3 days before expiration at 9:30 AM 
   * (confirmMessageStoredStatus()) : To prevent duplicate storage, check whether the data is present in the coupon message table , Save it to the table only if data doesn't exist.
   * message confirmation column -> default: false
   * changeCouponMessageCheckStatus() : When the user accesses and checks the message, the message confirmation column changes to true.
   </pre>
  

## Problem Solving
1. Coupon Code Generation Method
 > 1-1. Random String generation (Example : dmxcdyf8zo0t13t1ahya)
  * Coupon Code stored with Hash value can be fast search
    * Random code generation mixing 20 digits of English and numbers
    * Use java.util.Random class
    * nextBoolean() method return true(lower case),false(random numbers)
    * nextInt() method convertor returned numbers to 62 digit([0-9][a-z][A-Z])
    * Random String generation of a 20 digits arbitrary string connecting all characters created by repeating 20 times with StringBuffer 
    
 > 1-2. HashCode Generation (Example : 1828977362)    
  * Get the generated random code, create a Hash Code, and save it together
    * Use java.util.zip.CRC32
    * Change the random code to byte array
    * Create a CRC32 Long-form code using the byte array value update() function
      
  
  * Realistic performance issues
     * From a small number of data, random code can be inquired and generated.
     * However, when searching hundreds of millions of data, it costs a lot to check the code value.
     * Inquiry by storing hash code values created when issuing coupon codes (reduced from 40 seconds to 20 seconds when 9,991 pieces are stored at once).
