<?php

/*
|--------------------------------------------------------------------------
| Application Routes
|--------------------------------------------------------------------------
|
| Here is where you can register all of the routes for an application.
| It's a breeze. Simply tell Laravel the URIs it should respond to
| and give it the controller to call when that URI is requested.
|
*/

Route::get('/', function () {
    return view('welcome');
});

Route::group(['prefix'=>'api'],function(){
	Route::post('/login', ['uses'=>'ApiAuthController@login']);
	Route::get('/getuser',['uses' => 'ApiAuthController@getuser']);
	Route::get('/logout',['uses' => 'ApiAuthController@logout']);
	Route::get('/refresh',['uses' => 'ApiAuthController@refresh']);
	Route::post('/register',['uses' => 'ApiAuthController@register']);
	Route::get('/settings/password',['uses' => 'ApiUserController@password']);
	Route::get('/settings/name',['uses' => 'ApiUserController@name']);
	Route::get('/settings/email',['uses' => 'ApiUserController@email']);

	Route::get('/getallmedicines',['uses'=>'ApiActivityController@getallmedicine']);
	Route::get('/getmymedicines',['uses'=>'ApiActivityController@getmymedicine']);
	Route::get('/addmedicine',['uses'=>'ApiActivityController@addmedicine']);
	Route::get('/removemedicine',['uses'=>'ApiActivityController@removemedicine']);
	Route::get('/getschedule',['uses'=>'ApiActivityController@getschedule']);
	Route::get('/addschedule',['uses'=>'ApiActivityController@addschedule']);
	Route::get('/removeschedule',['uses'=>'ApiActivityController@removeschedule']);

});