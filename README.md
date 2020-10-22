# WebAPI

#### 目录
1.  [介绍](#介绍)
2.  [使用说明](#使用说明)
    - [身份验证](#身份验证)
    - [用户注册](#用户注册)
    - [IP定位](#IP定位)
    - [商品搜索](#商品搜索)
3.  [参与贡献](#参与贡献)
---
#### <a id="介绍">介绍</a>
该 API 将会提供以下服务：
1.  用户、订单、商家等信息查询接口
2.  卖家、买家用户注册接口
3.  商品搜索
4.  订单操作，包括：下单、取消订单、退货等
5.  交易操作
---
#### <a id="使用说明">使用说明</a>

1.  <a id="身份验证">身份验证</a>：  
    + 请求 URL:
    
        ```GET: /webapi/user/login```
    
    + 参数信息:
    
        |参数名|类型|必填|缺省值|说明|
        |:----:|:----:|:----:|:----:|:----:|
        |type|enum|否|buyer|用户类型(buyer或者seller)|
        |accountName|String|是|null|账户昵称(4-8个非数字字符)|
        |password|String|是|null|帐户密码(8-16个由大小写字母和数字组成的字符串)|
    
    + 请求示例:
    
        ```<ip>/webapi/user/login?type=buyer&accountName=abcde&password=Abc123456```
    
    + 响应示例:
        ```
        {
            "status": 0,
            "ID": 123456,
            "accountName": abcde,
            "nickName": Pony,
            "lease": null,
            "isLoggedIn": false
        }
        ```
      
        ```
        {
            "status": -1,
            "errMsg": 密码错误
        }
        ```
        
    + 字段说明:
    
      |字段名称|说明|
      |:----:|:----:|
      |status|状态码: 0(正常), -1(异常)|
      |type|用户类型|
      |ID|用户ID|
      |accountName|用户昵称|
      |errMsg|错误信息|
      
    + 登录Cookie及安全
        1.  Cookie有效期-1
        2.  HttpOnly属性为True
        3.  对Cookie综合时间、用户名和随机数进行加密
        ```secretString = MD5(name+passwordd+lastLoginTime```
    
2.  <a id="用户注册">用户注册</a>:
    + 请求 URL:
        
        ```POST: /webapi/user/register```
        
    + 参数信息:
    
        |参数名|类型|必填|缺省值|说明|
        |:----:|:----:|:----:|:----:|:----:|
        |type|enum|否|buyer|用户类型(buyer或者seller)|
        |accountName|String|是|null|账户名称(4-8个非特殊字符)|
        |nickName|String|是|null|昵称(4-8个非特殊字符)|
        |password|String|是|null|帐户密码(8-16个由大小写字母和数字组成的字符串)|
        |introduceSign|String|否|null|个人签名|
        |name|String|是|null|用户真实姓名|
        |identityNum|String|否|null|证件号码|
        |phoneNumber|String|是|null|电话号码|
        |gender|enum|否|secret|性别|
        |age|int|是|null|用户年龄|
    
    + 请求示例:
    
        ```<ip>/webapi/user/login?nickName=SB567&type=buyer&accountName=abcde&password=Abc123456&name=Tony&phoneNumber=15836274626&gender=male```
    
    + 响应示例:
        ```
        {
            "status": 0
        }
        ```
      
        ```
        {
            "status": -1,
            "errMsg": 密码格式错误
        }
        ```
        
    + 字段说明:
    
      |字段名称|说明|
      |:----:|:----:|
      |status|状态码: 0(正常), -1(异常)|
      |errMsg|错误信息|

3.  <a id="IP定位">IP定位</a>:
    + 请求 URL:
        
        ```GET: /webapi/geo/ip```
        
    + 参数信息:
    
        |参数名|类型|必填|缺省值|说明|
        |:----:|:----:|:----:|:----:|:----:|
        |ip|String|是|null|ip地址参数|
    
    + 请求示例:
    
        ```<ip>/webapi/geo/ip?ip=112.54.81.12```
    
    + 响应示例:
        ```
        {
            "status": 0,
            "ip": 112.54.81.12,
            "location":
            {
                "city": 威海市,
                "province": 山东省
            }
        }
        ```
      
        ```
        {
            "status": -1,
            "errMsg": error message
        }
        ```
        
    + 字段说明:
    
      |字段名称|说明|
      |:----:|:----:|
      |status|状态码: 0(正常), -1(异常)|
      |ip|请求时用到的ip地址|
      |location|位置信息|
      |city|城市|
      |province|省份|
      |errMsg|错误信息|
4.  <a id="商品搜索">商品搜索</a>：
    + 请求URL：
    
        ```GET: /webapi/search```
        
    + 参数信息：
        
        |参数名|类型|必填|缺省值|说明|
        |:----:|:----:|:----:|:----:|:----:|
        |searchType|enum|是|null|搜索目的(goods，不可为seller)|
        |searchName|String|是|null|搜索关键字|
        
    + 请求示例：
    
        ```<ip>/webapi/search?searchType=goods&searchName=universe```
        
    + 响应示例：
    
        ```
        {
            "status": 0,
            "goods" [
                {
                    "name":"Airbus A380",
                    "price":10,
                    "sold": 0,
                    "remaining": 1,
                    "picture": 不知道咋办,
                },
                {
                    "name":"Boeing 787",
                    "price":10,
                    "sold": 0,
                    "remaining": 1,
                    "picture": 不知道咋办,
                },
            ]
        }
        ```
        ```
        {
            "status": 1,
            "errMsg": "Did not choose search type.",   
        }
        ```
      
    + 字段说明：
    
        |字段名称|说明|
        |:----:|:----:|
        |status|状态码: 0(正常), -1(异常)|
        |goods|商品对象数组|
        |name|商品名称|
        |price|商品价格|
        |sold|商品已卖出数量|
        |remaining|商品剩余数量|
        |errMsg|错误信息|
        
---
#### <a id="参与贡献">参与贡献</a>

+ 戚瀚中
+ 陈岳涛
