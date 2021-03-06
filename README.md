# WebAPI

#### 目录
1.  [介绍](#介绍)
2.  [使用说明](#使用说明)
    - [身份验证](#身份验证)
    - [用户注册](#用户注册)
    - [IP定位](#IP定位)
    - [商品搜索](#商品搜索)
    - [卖家搜索](#卖家搜索)
    - [用户登出](#用户登出)
    - [收货地址](#收货地址)
    - [获取用户信息](#获取用户信息)
    - [修改用户信息](#修改用户信息)
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

1.  ##### <a id="身份验证">身份验证</a>：  
    + 请求 URL:
    
        ```GET: /webapi/user/login```
    
    + 参数信息:
    
        |参数名|类型|必填|缺省值|说明|
        |:----:|:----:|:----:|:----:|:----:|
        |accountName|String|是|null|账户昵称(4-8个非数字字符)|
        |password|String|是|null|帐户密码(8-16个由大小写字母和数字组成的字符串)|
    
    + 请求示例:
    
        ```<ip>/webapi/user/login?accountName=Test123&password=Abc123456```
    
    + 响应示例:
        ```
        {
            "status": 0,
            "ID": "123456",
            "accountName": "abcde",
            "nickName": "Pony",
            "lease": "null",
            "isLoggedIn": "1"
        }
        ```
      
        ```
        {
            "status": -1,
            "errMsg": "密码错误"
        }
        ```
        
    + 字段说明:
    
      |字段名称|说明|
      |:----:|:----:|
      |status|状态码: 0(正常), -1(异常)|
      |ID|用户ID|
      |accountName|账户名称|
      |nickName|用户昵称|
      |lease|租赁时长|
      |isLoggedIn|是否已经登陆(0-在线;1-不在线)|
      |errMsg|错误信息|
      
    + 登录Cookie及安全
        1.  Cookie有效期-1
        2.  HttpOnly属性为True
        3.  对Cookie综合时间、用户名和随机数进行加密
        ```secretString = MD5(name+passwordd+lastLoginTime```
    
2.  ##### <a id="用户注册">用户注册</a>:
    + 请求 URL:
        
        ```GET: /webapi/user/register```
        
    + 参数信息:
    
        |参数名|类型|必填|缺省值|说明|
        |:----:|:----:|:----:|:----:|:----:|
        |accountName|String|是|null|账户名称(4-8个非特殊字符)|
        |nickName|String|是|null|昵称(4-8个非特殊字符)|
        |password|String|是|null|帐户密码(8-16个由大小写字母和数字组成的字符串)|
        |introduceSign|String|否|""|个人签名|
        |name|String|是|null|用户真实姓名|
        |identityNum|String|否|""|证件号码|
        |phoneNumber|String|是|null|电话号码|
        |gender|enum|否|secret|性别(male, female, secret)|
        |age|int|是|null|用户年龄|
    
    + 请求示例:
    
        ```<ip>/webapi/user/register?accountName=Test123&password=Abc123456&name=Tony&phoneNumber=15906307587&gender=male&age=20&nickName=tony393&identityNum=230302199810165038```
    
    + 响应示例:
        ```
        {
            "status": 0
        }
        ```
      
        ```
        {
            "status": -1,
            "errMsg": "密码格式错误"
        }
        ```
        
    + 字段说明:
    
      |字段名称|说明|
      |:----:|:----:|
      |status|状态码: 0(正常), -1(异常)|
      |errMsg|错误信息|

3.  ##### <a id="IP定位">IP定位</a>:
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
            "ip": "112.54.81.12",
            "location":
            {
                "city": "威海市",
                "province": "山东省"
            }
        }
        ```
      
        ```
        {
            "status": -1,
            "errMsg": "error message"
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
      
4.  ##### <a id="商品搜索">商品搜索</a>：
    + 请求URL：
    
        ```GET: /webapi/goodsSearch```
        
    + 参数信息：
        
        |参数名|类型|必填|缺省值|说明|
        |:----:|:----:|:----:|:----:|:----:|
        |searchType|enum|是|null|搜索目的(goods，不可为seller)|
        |searchName|String|是|null|搜索关键字|
        
    + 请求示例：
    
        ```<ip>/webapi/goodsSearch?searchType=goods&searchName=universe```
        
    + 响应示例：
    
        ```
        {
            "status": 0,
            "goods": [
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

5.  ##### <a id="卖家搜索">卖家搜索</a>：
    + 请求URL：
    
        ```GET: /webapi/sellersSearch```
        
    + 参数信息：
        
        |参数名|类型|必填|缺省值|说明|
        |:----:|:----:|:----:|:----:|:----:|
        |searchType|enum|是|null|搜索目的(sellers，不可为goods)|
        |searchName|String|是|null|搜索关键字|
        
    + 请求示例：
    
        ```<ip>/webapi/sellersSearch?searchType=sellers&searchName=universe```
        
    + 响应示例：
    
        ```
        {
            "status": 0,
            "Shops" [
                {
                    "shop_id": 23,
                    "shop_name": "Airbus",
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
        |goods_id|商品id|
        |goods_name|商品名称|
        |goods_intro|商品介绍|
        |good_category_id|商品种类id|
        |goods_cover_img|封面图片|
        |origin_price|商品原价|
        |selling_price|商品现价|
        |stock_num|商品剩余数量|
        |tag|标签|
        |shop_id|卖家id|
        |shop_name|卖家名称|
        |errMsg|错误信息|
        
6.  ##### <a id="用户登出">用户登出</a>：
    + 请求 URL:
    
        ```GET: /webapi/user/logoff```
    
    + 参数信息:
    
        |参数名|类型|必填|缺省值|说明|
        |:----:|:----:|:----:|:----:|:----:|
        |accountName|String|是|null|账户名称|
    
    + 请求示例:
    
        ```<ip>/webapi/user/login?accountName=acivkdi```
    
    + 响应示例:
        ```
        {
            "status": 0
        }
        ```
      
        ```
        {
            "status": -1,
            "errMsg": "密码错误"
        }
        ```
        
    + 字段说明:
    
      |字段名称|说明|
      |:----:|:----:|
      |status|状态码: 0(正常), -1(异常)|
      |errMsg|错误信息|
      
7.  ##### <a id="收货地址">收货地址</a>：
    + 请求 URL:
    
        ```GET: /webapi/user/profile/address```
    
    + 参数信息:
    
        |参数名|类型|必填|缺省值|说明|
        |:----:|:----:|:----:|:----:|:----:|
        |option|String|是|null|三种操作: add, delete, modify, get|
        |accountName|String|是|null|账户名称(add, delete, modify, get)|
        |province|String|是|null|省份(add, modify)|
        |city|String|是|null|城市(add, modify)|
        |county|String|否|""|地区(add. modify)|
        |detail|String|是|null|详细地址(add, modify)|
        |consignee|String|是|null|收货人(add, modify)|
        |phoneNumber|String|是|null|收货人电话(add, modify)|
        |status|String|是|null|地址默认值(1-默认, 2-非默认)(add, modify)|
        |ID|String|是|null|地址ID(delete, modify)|
    
    + 请求示例:
    
        ```<ip>/webapi/user/profile/address?option=add&accountName=Test123&province=山东&city=威海&county=文登&detail=BJTU&consignee=tony&phoneNumber=16542597416&status=1```  
          
        ```<ip>/webapi/user/profile/address?option=delete&accountName=Test123&ID=45123545875```  
          
        ```<ip>/webapi/user/profile/address?option=modify&accountName=Test123&province=山东&city=威海&county=文登&detail=BJTU&consignee=tony&phoneNumber=16542597416&status=1&ID=45123545875```  
          
        ```<ip>/webapi/user/profile/address?option=get&accountName=Test123```
    
    + 响应示例:
        ```
        {
            # add
            "status": 0
        }
        ```
      
        ```
        {
            # delete
            "status": 0
        }
        ```
      
        ```
        {
            # modify
            "status": 0
        }
        ```
      
        ```
        {
            # get
            "status": 0,
            "addresses":
            [
                {
                    "addid": "456451312331",
                    "userId": "45645115156315361",
                    "province": "山东",
                    "city": "威海",
                    "county": "文登",
                    "detail": "BJTU",
                    "consignee": "tony",
                    "phoneNumber": "16542597416",
                    "status": 1,
                    "createTime": "2020-10-28 01:56:56",
                    "upTime": "020-10-28 01:56:56",
                }
            ]
        }
        ```
      
        ```
        {
            "status": -1,
            "errMsg": "密码错误"
        }
        ```
        
    + 字段说明:
    
      |字段名称|说明|
      |:----:|:----:|
      |status|状态码: 0(正常), -1(异常)|
      |errMsg|错误信息|
      |addid|地址ID|
      |userId|用户ID|
      
8.  ##### <a id="获取用户信息">获取用户信息</a>：
    + 请求 URL:
        
        ```GET: /webapi/user/profile/get```
        
    + 参数信息:
        
        |参数名|类型|必填|缺省值|说明|
        |:----:|:----:|:----:|:----:|:----:|
        |accountName|String|是|null|账户名称|
        
    + 请求示例:
    
        ```<ip>/webapi/user/profile/get?accountName=Test123```
    
    + 响应示例:
        ```
        {
            "status": 0，
            "nickName": "tony363",
            "introduceSign": "",
            "name": "",
            "identityNum": "",
            "phoneNumber": "",
            "gender": "",
            "age": 38,
            "avatar": "39xcvuxd3980890de9908"
        }
        ```
      
        ```
        {
            "status": -1,
            "errMsg": "错误信息"
        }
        ```
        
    + 字段说明:
    
      |字段名称|说明|
      |:----:|:----:|
      |status|状态码: 0(正常), -1(异常)|
      |errMsg|错误信息|
      |nickName|用户昵称|
      |introduceSign|个人签名|
      |name|真实姓名|
      |identityNum|身份证号|
      |phoneNumber|电话号码|
      |gender|用户性别|
      |age|用户年龄|
      |avatar|图片base64二进制|
    
9.  ##### <a id="修改用户信息">修改用户信息</a>：
    + 请求 URL:
            
        ```GET: /webapi/user/profile/modify```
            
    + 参数信息:
        
        |参数名|类型|必填|缺省值|说明|
        |:----:|:----:|:----:|:----:|:----:|
        |accountName|String|是|null|账户名称|
        |nickName|String|是|null|用户昵称|
        |password|String|是|null|账号密码|
        |introduceSign|String|是|null|个人签名|
        |phoneNumber|String|是|null|电话号码|
        |gender|String|是|null|用户性别|
        |age|int|是|null|用户年龄|
        |avatar|String|是|null|图片base64二进制|
        
    + 请求示例:
    
        ```<ip>/webapi/user/profile/modify?accountName=Test123&nickName=vcxz&password=fdsvc2323&introduceSign=vcxzvc&phoneNumber=15623458756&gender=secret&age=39&avatar=vcx3c9898738d9c```
    
    + 响应示例:
        ```
        {
            "status": 0
        }
        ```
      
        ```
        {
            "status": -1,
            "errMsg": "错误信息"
        }
        ```
        
    + 字段说明:
    
      |字段名称|说明|
      |:----:|:----:|
      |status|状态码: 0(正常), -1(异常)|
      |errMsg|错误信息|
               
---
#### <a id="参与贡献">参与贡献</a>

+ 戚瀚中
+ 陈岳涛
