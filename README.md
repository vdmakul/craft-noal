craft-noal
==========

REST service application for [initial task](doc/task.md)

API
---

The only request that is allowed to be anonymous is user creation:  
  
 * **/user/create** POST  
creates new user with parameters:
    * **userAccount**: unique user identifier
    * **name**: user name  
    * **password**: user password  
  
Other requests requires basic HTTP authentication within credentials created before   

 * **/user** GET  
responses information about logged user  
  
 * **/loan/apply** POST  
request for loan application with parameters:  
     * **amount**: requested amount      
     * **term**: term formatted as 'yyyy-MM-dd'    
   
 * **/loan/{id}** GET  
responses information about specified by *id* loan  
  
 * **/loan/{id}/extend** POST  
request for specified by *id* loan extension  

 * **/loans** GET  
responses list of all logged user loans   

 * **/applications** GET  
responses list of all logged user loan applications