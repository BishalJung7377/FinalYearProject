
# VetmentsFashion Nepal App
<a href="https://i.ibb.co/Nmrfrc1/Vetments.png" width="200" height="200"><img src="https://i.ibb.co/Nmrfrc1/Vetments.png" width="200" height="200" title=" VetmentsFashion Nepal" alt=" VetmentsFashion Nepal"></a>




> A VetmentsFashion Nepal app 

> An app which can create and store task as well as give users ability to set dates reminders about their day to day activity

> tags: app,  VetmentsFashion Nepal, android, github
---

## Table of Contents
### Click here to jump to these pages:-
- [Branching Stages](#branching-stages)
- [Architecture](#architecture)
- [Installation](#installation)
- [Project Pictures & GIFs](#gifs)
- [Documents](#documents)


---

# Branching Stages
```bash
├── Master
|
|---->|─ Main Branch
│     ├── API Branch
│     ├── WearOS Branch
│   
```

---

# Architecture 
## Adapter
* `FavouriteItemAdapter`<br>
* `ItemCartAdapter`<br>
* `NewItemAdapter`<br>
* `RecentItemAdapter`<br>
* `SmalliconItemItemAdapter`<br>
* `TestimonialAdapter`<br>
* `ViewAllItemAdapter`<br>


## AddEditTask
* `LoginActivity`  <br>
* `RegisterActivity`  <br>
* `ItemDisplayActivity`  <br>
* `GoogleMapActivity`  <br>
* `AboutPageActivity`  <br>
* `SplashScreenActivity`  <br>
* `UpdatePasswordActivity`  <br>
* `ViewAllItemActivity`  <br>


* `AuthViewModel` <br>
* `AuthViewModelFactory` <br>
* `RegisterViewModelFactory` <br>
* `CartFragments`<br>
* `FavouritesFragment`<br>
* `HomeFragments`<br>
* `ProfileFragments`<br>


## Database
* `BuyerDb`  <br>

## DAO
*  `BuyerDAO` <br>
*  `CartItemDAO`<br>
*  `FavouriteItemDAO`<br>
*  `ItemDAO`<br>
*  `ViewAllItemDAO`<br>

## Convertors
* `CartItemConverter` <br>
* `CartItemUserConverter` <br>
* `ItemConverter` <br>
* `UserConverter` <br>


## Repository
* `BuyerCartItemRepository`  <br>
* `BuyerFavouriteItemRepository`  <br>
* `BuyerRepository`  <br>
* `DiscoverItemRepository`  <br>

## Entity
* `Buyer`  <br>
* `FavouriteItemModel`  <br>
* `ItemCartModel`  <br>
* `NewItemModel`  <br>
* `RecentItemModel`  <br>
* `ViewAllItemModel`  <br>

# Response
## `Buyer`  <br>

### Response
* `BuyerDataResponse`  <br>
* `BuyerImageResponse`  <br>
* `BuyerLoginResponse`  <br>
* `BuyerPasswordUpdateResponse`  <br>
* `BuyerRegisterResponse`  <br>
* `BuyerUpdateResponse`  <br>

## `CartItem`  <br>

### Response
* `CartItemInsertResponse`  <br>
* `CartItemResponse`  <br>
* `DeleteCartItemResponse`  <br>

## `DiscoverItem`  <br>

### Response
* `DiscoverItemResponse`  <br>
* `ViewAllItemResponse`  <br>

## `FavouriteItem`  <br>

### Response
* `BuyerFavouriteItemInsertResponse`  <br>
* `BuyerFavouriteItemResponse`  <br>
* `DeleteFavouriteItemResponse`  <br>
---




## Example of how the code looks

```Kotlin
// code away!

    private fun cartItems() {
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val catItemRepository = BuyerCartItemRepository()
                val response = catItemRepository.getBuyercartItem()
                println(response.message)
                if (response.success == true) {
                    val cartItemList = response.message
                    println("this is $cartItemList")
                    BuyerDb.getInstance(requireContext()).getCartItemDAO().deleteCartItem()
                    BuyerDb.getInstance(requireContext()).getCartItemDAO().insertCartItem(cartItemList as List<ItemCartModel>)
                    withContext(Dispatchers.Main) {
                        println(response)
                        val adapter = ItemCartAdapter(
                            cartItemList as ArrayList<ItemCartModel>,
                            requireContext()
                        )
                        recyclerView.layoutManager = LinearLayoutManager(context)
                        recyclerView.adapter = adapter
                    }
                } else {

                }
            }
        } catch (ex: Exception) {
            Toast.makeText(
                context,
                "Error : ${ex.toString()}", Toast.LENGTH_SHORT
            ).show()
        }

    }

```

---

## How to install and use app

- First register for an account by filling up the form
- After regestering Login with same details from registration page
- To add a item to favourite tap the 'Heart Shaped icon' icon
- To add a item to cart tap the 'Add to cart' button
- To add a item from favourite to cart tap the 'Cart' icon
- To delete a item from favourite or cart tap the 'Trash Can' icon
- To Update user info go to profile and fill valid data and press update button
- To get side navigation bar slide to right from left corner of device
- To View Map press on 'Map' icon of side navigation bar
- To View About Us press on 'About Us' icon of side navigation bar
- To logout press on 'Log Out' icon of side navigation bar



### Clone

- Clone this repo to your local machine using `https://github.com/stw300cem/finalassignment-BishalJung7377`

### Setup

- If you want to edit the code:

> update and install these items

```shell
$ Android Studio
$ Latest Gradel
```

> Item needed to run code

```shell
$ Android Virtual Device
$ Android Phone running Android 5.5 or higher
```


---
## Pictures of various pages
Register          |  Sign-IN                      | Adding Items              
:----------------------------:|:--------------------------------------:|:----------------------:|
<img src = "https://i.ibb.co/x2w2XV1/register.png" width="288" height="512">  |  <img src = "https://i.ibb.co/QDLyNMr/Login.png" width="288" height="512">        |  <img src = "https://i.ibb.co/4MsKMZy/Cart.png" width="288" height="512">


---

## Gifs

> Gifs of various featurs of app

Register          |  LogIn                      | Add To Cart              |  Add TO Favourite
:----------------------------:|:--------------------------------------:|:----------------------:|:-----------------
<img src = "https://i.ibb.co/47h3whh/Register-Indiviual.gif" width="200" height="360">  |  <img src = "https://i.ibb.co/6JBhHjc/Login-Individual.gif" width="200" height="360">        |  <img src = "https://i.ibb.co/q1MCxjF/Add-To-Cart.gif" width="200" height="360">  | <img src = "https://i.ibb.co/Ch201Ym/Favourite-Add.gif" width="200" height="360">

#
Home Page          |  SideNavigation                     | Search Item              |  Image Update
:----------------------------:|:--------------------------------------:|:----------------------:|:-----------------
<img src = "https://i.ibb.co/kBQ8BQT/Home-Page-Navigation-Page.gif" width="200" height="360">  |  <img src = "https://i.ibb.co/71cCbJL/Side-Navigation.gif" width="200" height="360">        |  <img src = "https://i.ibb.co/XWCftn6/Search.gif" width="200" height="360">  | <img src = "https://i.ibb.co/n31rXy8/User-Image-Update.gif" width="200" height="360">

---

#
Delete Item          |  Logout                     | Google Map            |  About Us
:----------------------------:|:--------------------------------------:|:----------------------:|:-----------------
<img src = "https://i.ibb.co/T2Zt2VV/Delete-Item.gif" width="200" height="360">  |  <img src = "https://i.ibb.co/Mn9TB0f/Logout.gif" width="200" height="360">        |  <img src = "https://i.ibb.co/g7dsGV8/Map.gif" width="200" height="360">  | <img src = "https://i.ibb.co/9c407rx/AbouUs.gif" width="200" height="360">

---




# Documents
## MVVM 
<p align="center">
<img src="https://i.ibb.co/x6CRfgY/mvvm.png" alt="" width="500" height="500">
</p>
- MVVM stands for Model, View, ViewModel.

<p>-Model: This holds the data of the application. It cannot directly talk to the View. Generally, it’s recommended to expose the data to the ViewModel through Observables.</p>
<p>- View: It represents the UI of the application devoid of any Application Logic. It observes the ViewModel.</p>
<p>- ViewModel: It acts as a link between the Model and the View. It’s responsible for transforming the data from the Model. It provides data streams to the View. It also uses hooks or callbacks to update the View. It’ll ask for the data from the Model.</p>

---
