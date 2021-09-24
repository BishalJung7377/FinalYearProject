package com.bishaljung.vetementsfashionnepal

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.bishaljung.vetementsfashionnepal.DAO.*
import com.bishaljung.vetementsfashionnepal.Database.BuyerDb
import com.bishaljung.vetementsfashionnepal.Entity.Buyer
import com.bishaljung.vetementsfashionnepal.Entity.ItemCartModel
import com.bishaljung.vetementsfashionnepal.Repository.BuyerCartItemRepository
import com.bishaljung.vetementsfashionnepal.Repository.BuyerFavouriteItemRepository
import com.bishaljung.vetementsfashionnepal.Repository.BuyerRepository
import com.bishaljung.vetementsfashionnepal.Repository.DiscoverItemRepository
import com.bishaljung.vetementsfashionnepal.api.ServiceBuilder
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

class VetementsFashionTest {

    private lateinit var buyerRepository: BuyerRepository
    private lateinit var buyerCartItemRepository: BuyerCartItemRepository
    private lateinit var buyerFavouriteItemRepository: BuyerFavouriteItemRepository
    private lateinit var viewallItemRepository: DiscoverItemRepository


    // -----------------------------User Testing-----------------------------
    @Test
    fun checkBuyer() = runBlocking {
        buyerRepository = BuyerRepository()
        val response = buyerRepository.checkBuyer("test123@gmail.com", "apple")
        val expectedResult = true
        val actualResult = response.success
        Assert.assertEquals(expectedResult, actualResult)
    }

    @Test
    fun registerBuyer() = runBlocking {
        val buyer =
            Buyer(
                BuyerFullName = "Test User",
                BuyerEmail = "test123@gmail.com",
                BuyerGender = "Male",
                BuyerAge = 21,
                BuyerPhone = "987654321",
                BuyerPassword = "apple"
            )
        buyerRepository = BuyerRepository()
        val response = buyerRepository.registerBuyer(buyer)
        val expectedResult = true
        val actualResult = response.success
        Assert.assertEquals(expectedResult, actualResult)
    }


    //     -----------------------------Adding Item To Favourite Testing-----------------------------
    @Test
    fun registerFavouriteitem() = runBlocking {
        buyerRepository = BuyerRepository()
        buyerFavouriteItemRepository = BuyerFavouriteItemRepository()

        ServiceBuilder.token =
            "Bearer " + buyerRepository.checkBuyer("test123@gmail.com", "apple").buyerToken
        val repository = BuyerFavouriteItemRepository()
        val response = repository.insertBuyerFavouriteItem(id = "60705bd02677d31114b79a7f")
        val expectedResult = true
        val actualResult = response.success
        Assert.assertEquals(expectedResult, actualResult)
    }

    //     -----------------------------Adding Item To Cart Testing-----------------------------
    @Test
    fun addCart() = runBlocking {
        buyerRepository = BuyerRepository()
        buyerCartItemRepository = BuyerCartItemRepository()


        ServiceBuilder.token =
            "Bearer " + buyerRepository.checkBuyer("test123@gmail.com", "apple").buyerToken
        val repository = BuyerCartItemRepository()
        val response = repository.insertBuyercartItem(id = "60705bd02677d31114b79a7f")
        val expectedResult = true
        val actualResult = response.success
        Assert.assertEquals(expectedResult, actualResult)
    }

    //     -----------------------------User Info Update Testing-----------------------------
    @Test
    fun updateUser() = runBlocking {
        buyerRepository = BuyerRepository()
        val buyer =
            Buyer(BuyerFullName = "Test User Name", BuyerPhone = "987654282")
        ServiceBuilder.token =
            "Bearer " + buyerRepository.checkBuyer("test123@gmail.com", "apple").buyerToken
        val expectedResult = true
        val actualResult = buyerRepository.updateBuyerData(buyer).success
        Assert.assertEquals(expectedResult, actualResult)

    }
    //     -----------------------------Show User Cart Item Testing-----------------------------

    @Test
    fun showCart() = runBlocking {
        buyerRepository = BuyerRepository()
        buyerCartItemRepository = BuyerCartItemRepository()


        ServiceBuilder.token =
            "Bearer " + buyerRepository.checkBuyer("test123@gmail.com", "apple").buyerToken
        val catItemRepository = BuyerCartItemRepository()
        val response = catItemRepository.getBuyercartItem()
        val expectedResult = true
        val actualResult = response.success
        Assert.assertEquals(expectedResult, actualResult)
    }


    //     -----------------------------Show User Favourite Item Testing-----------------------------
    @Test
    fun showFavourite() = runBlocking {
        buyerRepository = BuyerRepository()
        buyerFavouriteItemRepository = BuyerFavouriteItemRepository()

        ServiceBuilder.token =
            "Bearer " + buyerRepository.checkBuyer("test123@gmail.com", "apple").buyerToken
        val repository = BuyerFavouriteItemRepository()
        val response = repository.getBuyerFavouriteItem()
        val expectedResult = true
        val actualResult = response.success
        Assert.assertEquals(expectedResult, actualResult)
    }


    //     -----------------------------Display All Item Testing-----------------------------
    @Test
    fun showAllItems() = runBlocking {
        buyerRepository = BuyerRepository()
        viewallItemRepository = DiscoverItemRepository()

        ServiceBuilder.token =
            "Bearer " + buyerRepository.checkBuyer("test123@gmail.com", "apple").buyerToken
        val viewallItemRepository = DiscoverItemRepository()
        val response = viewallItemRepository.ViewAllItems()
        val expectedResult = true
        val actualResult = response.success
        Assert.assertEquals(expectedResult, actualResult)
    }

    //     -----------------------------Delete Cart Item Testing-----------------------------
    @Test
    fun userCartItemDelete() = runBlocking {
        buyerRepository = BuyerRepository()
        buyerCartItemRepository = BuyerCartItemRepository()

        ServiceBuilder.token =
            "Bearer " + buyerRepository.checkBuyer("test123@gmail.com", "apple").buyerToken
        val cartItemdeleteRepository = BuyerCartItemRepository()
        val response = cartItemdeleteRepository.deleteBuyercartItem(id = "60705bd02677d31114b79a7f")
        val expectedResult = true
        val actualResult = response.success
        Assert.assertEquals(expectedResult, actualResult)
    }

    //     -----------------------------Delete Favourite Item Testing-----------------------------
    @Test
    fun userFavouriteItemDelete() = runBlocking {
        buyerRepository = BuyerRepository()
        buyerCartItemRepository = BuyerCartItemRepository()


        ServiceBuilder.token =
            "Bearer " + buyerRepository.checkBuyer("test123@gmail.com", "apple").buyerToken
        val favouriteItemdeleteRepository = BuyerFavouriteItemRepository()
        val response =
            favouriteItemdeleteRepository.deleteBuyerFavouriteItem(id = "60705bd02677d31114b79a7f")
        val expectedResult = true
        val actualResult = response.success
        Assert.assertEquals(expectedResult, actualResult)
    }

    //     -----------------------------Fail test Display All Item Testing-----------------------------
    @Test
    fun showAllItemsFailTest() = runBlocking {
        buyerRepository = BuyerRepository()
        viewallItemRepository = DiscoverItemRepository()

        ServiceBuilder.token =
            "Bearer " + buyerRepository.checkBuyer("test123@gmail.com", "apple123454").buyerToken
        val viewallItemRepository = DiscoverItemRepository()
        val response = viewallItemRepository.ViewAllItems()
        val expectedResult = true
        val actualResult = response.success
        Assert.assertEquals(expectedResult, actualResult)
    }

    //     -----------------------------Fail test Delete Cart Item Testing-----------------------------
    @Test
    fun userCartItemDeleteFailTest() = runBlocking {
        buyerRepository = BuyerRepository()
        buyerCartItemRepository = BuyerCartItemRepository()

        ServiceBuilder.token =
            "Bearer " + buyerRepository.checkBuyer("test123@gmail.com", "apple123123").buyerToken
        val cartItemdeleteRepository = BuyerCartItemRepository()
        val response = cartItemdeleteRepository.deleteBuyercartItem(id = "60705bd02677d31114b79a7f")
        val expectedResult = true
        val actualResult = response.success
        Assert.assertEquals(expectedResult, actualResult)
    }

    //     ----------------------------- Fail Test Delete Favourite Item Testing-----------------------------
    @Test
    fun userFavouriteItemDeleteFailTest() = runBlocking {
        buyerRepository = BuyerRepository()
        buyerCartItemRepository = BuyerCartItemRepository()


        ServiceBuilder.token =
            "Bearer " + buyerRepository.checkBuyer("test123@gmail.com", "apple123123").buyerToken
        val favouriteItemdeleteRepository = BuyerFavouriteItemRepository()
        val response =
            favouriteItemdeleteRepository.deleteBuyerFavouriteItem(id = "60705bd02677d31114b79a7f")
        val expectedResult = true
        val actualResult = response.success
        Assert.assertEquals(expectedResult, actualResult)
    }

}
