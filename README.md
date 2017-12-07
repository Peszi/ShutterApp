# ShutterApp

## Description 

Android photo sharing app. Take photos, edit them and send to friends in a simple way.

## App structure

### Basic

The first part of the application is authorization (logging in, signing up, etc.). 
> Filling form in "Log in" activity (welcome screen) allows you to get your unique API key which is needed in main appliction part called "Shutter". Every data request is using key to indentify client (checking access to proper photo, getting specified user friends list..).

![UI STRUCTURE](http://shutterapp.pl/img/uiAuthStructure.png)

### Shutter

The ShutterActivity is written using MVP pattern which allows me to quickly manage applications logic.

- **api**
> - **ShutterDataManager** - api requests (contains most of the requesters, stores photos, friends and invites list in one place)
> - **AsyncTasks** for simple requests like delete or add friend.
> - **Threads** and **Queues** for photo uplading and downloading.

- **presenter**
> - **CameraEditorPresenter** - photo editing tools
> - **CameraHolderPresenter** - camera controlling hardware
> - **ManageCameraPresenter** - photo managing  
> - **ManageFriendsPresenter** - friends managing (showing, removing, searching..)
> - **ManagePhotosPresenter** - photo browsing

- **ui**
> Three fragments for separete app features.

![UI STRUCTURE](http://shutterapp.pl/img/uiStructure.png)

## API
**ShutterAPI**

The application RESTful API is based on the android tutorial from [Androidhive](https://www.androidhive.info/2015/03/android-hosting-php-mysql-restful-services-to-digitalocean/). 
Please check out this useful site first.

**RESTful** api service using **PHP**, **Slim** and **MariaDB**.

Structure of API:

![API STRUCTURE](http://shutterapp.pl/img/apiDialog.png)

Api URL http://www.shutterapi.pl/shutter/v1/

### Requests :

All requests are returning data in **JSON** format (except image data "image/jpeg" format). 
> Example of server JSON response (Get images list):
```json
{
	"error": false,
	"message": "Images ids readed succesfully!",
	"images": [
		{
			"id": 999,
			"creator_id": 1,
			"creator_name": "ME",
			"is_me": 1,
			"created_at": "2016-06-06 12:00:00"
		}
	]
}
 ```

### Basic

You do **not** need to provide authorization parameter.

- **Manage**

> Base requests using for managing user account. 

| URL           |	       Method |  	Parameters  |	 Description  |
| ------------- | :---: | ------------- | ------------- |
| ../register   | `POST`  | name, email, password | User registration  |
| ../login  | `POST`  | name, email  | Returning the user API key  |

### Authorized

You do **have** to provide authorization parameter.

- **Relations**

> Relations beetween users.

> **Invites**
> are one way relations **user => friend** or back.

> **Friends**
> adding is equal to accepting an invite (one way relation have to exist before **friend => user**).
> At the same time removing an invite/friend is deleting one way **(Invited)** or/and two way relation **(Friend)**.

> **Relations:**
> - Stranger - **no** user to stranger/friend relation
> - Invite - is a one way relation **(user => stranger/friend)**
> - Invited - is a one way relation **(stranger/friend => user)**
> - Friend - is a two way relation **(user <= stranger/friend AND stranger/friend => user)**

| URL           |	       Method |  	Parameters  |	 Description  |
| ------------- | :---: | ------------- | ------------- |
| ../search/:keyword   | `GET`  | authorization | Returning list of users with keyword in name  |
| ../invites/:id  | `POST`  | authorization | Creating an invite (one way relation)  |
| ../invites/:id  | `DELETE`  | authorization | Removing an invite |
| ../invites  | `GET`  | authorization | Returning list of invites |
| ../friends/:id  | `POST`  | authorization | Adding a friend (accept an invite)  |
| ../friends/:id  | `DELETE`  | authorization | Removing a friend (remove an invite)  |
| ../friends  | `GET`  | authorization | Returning list of friends |

- **Images**

> Manipulating user photos. Getting friends photos etc.

> **Posting**
> an image require image data in **Base64** encoding format and recipient (user) **id**.
> Every image recipient require a new parameter (ex."..user[]=<id>&user[]=<id2>..").<ENTER>
>
> Android code:
> ```java
> Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT)
> ```

> **Getting**
> an image.
>
> Android code:
> ```java
> BitmapFactory.decodeStream(requestConnection.getInputStream())
> ```

| URL           |	       Method |  	Parameters  |	 Description  |
| ------------- | :---: | ------------- | ------------- |
| ../images   | `POST`  | authorization, user[], image | Posting an image and attaching to users |
| ../images/:id   | `GET`  | authorization | Getting image data |
| ../images/:id   | `DELETE`  | authorization | Removing an image |
| ../images   | `GET`  | authorization | Getting images list |

## Author
- Mateusz Moskala - Peszi - peszidev@gmail.com

## License

Distributed under the **GNU** license. See ``LICENSE`` for more information.


