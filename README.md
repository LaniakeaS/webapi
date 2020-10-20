# WebAPI

#### 介绍
该 API 将会提供以下服务：
1.  用户、订单、商家等信息查询接口
2.  卖家、买家用户注册接口
3.  商品搜索
4.  订单操作，包括：下单、取消订单、退货等
5.  交易操作
---
#### 使用说明

1.  身份验证：  
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
            "status": true,
            "type": buyer,
            "ID": 123456,
            "accountName": "abcde",
        }
        ```
      
        ```
        {
            "status": false,
            "errMsg": "密码错误"
        }
        ```
        
    + 字段说明:
    
      |字段名称|说明|
      |:----:|:----:|
      |status|响应状态|
      |type|用户类型|
      |ID|用户ID|
      |accountName|用户昵称|
      |errMsg|错误信息|
    
2.  用户注册:
    + 请求 URL:
        
        ```POST: /webapi/user/register```
        
    + 参数信息:
    
        |参数名|类型|必填|缺省值|说明|
        |:----:|:----:|:----:|:----:|:----:|
        |type|enum|否|buyer|用户类型(buyer或者seller)|
        |accountName|String|是|null|账户名称(4-8个非特殊字符)|
        |nickName|String|是|null|昵称(4-8个非特殊字符)|
        |password|String|是|null|帐户密码(8-16个由大小写字母和数字组成的字符串)|
        |introduceSign|String|否|""|个人签名|
        |name|String|是|null|用户真实姓名|
        |phoneNumber|String|是|null|电话号码|
        |gender|enum|否|secret|性别|
        |age|int|是|null|用户年龄|
    
    + 请求示例:
    
        ```<ip>/webapi/user/login?nickName=SB567&type=buyer&accountName=abcde&password=Abc123456&name=Tony&phoneNumber=15836274626&gender=male```
    
    + 响应示例:
        ```
        {
            "status": true
        }
        ```
      
        ```
        {
            "status": false,
            "errMsg": "密码格式错误"
        }
        ```
        
    + 字段说明:
    
      |字段名称|说明|
      |:----:|:----:|
      |status|响应状态|
      |errMsg|错误信息|

3.  IP定位:
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
            "ip": 112.54.81.12,
            "location":
            {
                "city": 威海市,
                "province": 山东省
            }
        }
        ```
        
    + 字段说明:
    
      |字段名称|说明|
      |:----:|:----:|
      |ip|请求时用到的ip地址|
      |location|位置信息|
      |city|城市|
      |province|省份|

---
#### 参与贡献

+ 戚瀚中
+ 陈岳涛
