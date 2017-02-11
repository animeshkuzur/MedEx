<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;

use App\Http\Requests;
use Tymon\JWTAuth\Facades\JWTAuth;

use App\User;

class ApiUserController extends Controller
{
    public function password(Request $request){
    	try {
            if (!$user = JWTAuth::parseToken()->authenticate()) {
                return response()->json(['error' => 'user_not_found'], 404);
            }
        } catch (\Tymon\JWTAuth\Exceptions\TokenExpiredException $e) {
            return response()->json(['error' => 'token_expired'], $e->getStatusCode());
        } catch (\Tymon\JWTAuth\Exceptions\TokenInvalidException $e) {
            return response()->json(['error' => 'token_invalid'], $e->getStatusCode());
        } catch (\Tymon\JWTAuth\Exceptions\JWTException $e) {
            return response()->json(['error' => 'token_absent'], $e->getStatusCode());
        }
        $data = $request->only('old_password','new_password');
        if(empty($data['old_password']) || empty($data['new_password'])){
        	return response()->json(['error' => 'parameters missing']);
        }
        
        if(\Hash::check($data['old_password'],$user->password)){
        	$temp = User::where('id',$user->id)->update(['password'=>bcrypt($data['new_password'])]);
        	return response()->json(['info' => 'password changed']);
        }
        else{
        	return response()->json(['error' => 'invalid password']);
        }
    }

    public function name(Request $request){
    	try {
            if (!$user = JWTAuth::parseToken()->authenticate()) {
                return response()->json(['error' => 'user_not_found'], 404);
            }
        } catch (\Tymon\JWTAuth\Exceptions\TokenExpiredException $e) {
            return response()->json(['error' => 'token_expired'], $e->getStatusCode());
        } catch (\Tymon\JWTAuth\Exceptions\TokenInvalidException $e) {
            return response()->json(['error' => 'token_invalid'], $e->getStatusCode());
        } catch (\Tymon\JWTAuth\Exceptions\JWTException $e) {
            return response()->json(['error' => 'token_absent'], $e->getStatusCode());
        }
        $data = $request->only('name');
        if(empty($data['name'])){
        	return response()->json(['error' => 'parameters missing']);
        }
        $temp = User::where('id',$user->id)->update(['name'=>$data['name']]);
        return response()->json(['info' => 'name changed']);
    }

    public function email(Request $request){
    	try {
            if (!$user = JWTAuth::parseToken()->authenticate()) {
                return response()->json(['error' => 'user_not_found'], 404);
            }
        } catch (\Tymon\JWTAuth\Exceptions\TokenExpiredException $e) {
            return response()->json(['error' => 'token_expired'], $e->getStatusCode());
        } catch (\Tymon\JWTAuth\Exceptions\TokenInvalidException $e) {
            return response()->json(['error' => 'token_invalid'], $e->getStatusCode());
        } catch (\Tymon\JWTAuth\Exceptions\JWTException $e) {
            return response()->json(['error' => 'token_absent'], $e->getStatusCode());
        }
        $data = $request->only('email');
        if(empty($data['email'])){
        	return response()->json(['error' => 'parameters missing']);
        }
        $temp = User::where('id',$user->id)->update(['email'=>$data['email']]);
        return response()->json(['info' => 'email changed']);
    }

    
}
