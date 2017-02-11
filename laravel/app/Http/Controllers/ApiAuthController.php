<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;

use App\Http\Requests;
use Tymon\JWTAuth\Facades\JWTAuth;

use App\User;

class ApiAuthController extends Controller
{
    public function login(Request $request){
    	$data = $request->only('email','password');
        if(empty($data['email']) || empty($data['password'])){
            return response()->json(['error' => 'parameters missing']);
        }
        try {
            if (!$token = JWTAuth::attempt($data)) {
                return response()->json(['error' => 'invalid credentials'], 401);
            }
        } 
        catch (JWTException $e) {
            return response()->json(['error' => 'could not create token'], 500);
        }

        $req_data = User::where('email',$data['email'])->get();

        return response()->json(['info' => $req_data,'token'=>$token]);

    }

    public function getuser()
    {
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
        if(empty($user->cont_acc)){
            return response()->json(['error' => 'select a cont_acc']);
        }
        $data = User::where('id',$user->id)->get();
        return response()->json(['info' => $data]);
    } 

    public function logout(Request $request)
    {
        try {
        	JWTAuth::invalidate($request->input('token'));
        	return response()->json(['info' => 'token_destroyed']);
        }
        catch (\Tymon\JWTAuth\Exceptions\TokenExpiredException $e) {
            return response()->json(['error' => 'token_expired'], $e->getStatusCode());
        } catch (\Tymon\JWTAuth\Exceptions\TokenInvalidException $e) {
            return response()->json(['error' => 'token_invalid'], $e->getStatusCode());
        } catch (\Tymon\JWTAuth\Exceptions\JWTException $e) {
            return response()->json(['error' => 'token_absent'], $e->getStatusCode());
        }
    }

    public function refresh()
    {
        $token = JWTAuth::getToken();
        if (!$token) {
            return response()->json(['error' => 'token_absent']);
        }
        try {
            $refreshedToken = JWTAuth::refresh($token);
            return response()->json(['token' => $refreshedToken]);
        } 
        catch (\Tymon\JWTAuth\Exceptions\TokenExpiredException $e) {
            return response()->json(['error' => 'token_expired'], $e->getStatusCode());
        } catch (\Tymon\JWTAuth\Exceptions\TokenInvalidException $e) {
            return response()->json(['error' => 'token_invalid'], $e->getStatusCode());
        } catch (\Tymon\JWTAuth\Exceptions\JWTException $e) {
            return response()->json(['error' => 'token_absent'], $e->getStatusCode());
        } 
    }

    public function register(Request $request){
    	$data = $request->only('name','email','password','sex','age','phone');
    	if(empty($data['name']) || empty($data['email']) || empty($data['password']) || empty($data['phone'])){
            return response()->json(['error'=>'parameters missing']);
        }
        $data['password'] = bcrypt($data['password']);
        try{
            $user=\DB::table('users')->insert([
                'name' => $data['name'],
                'email' => $data['email'],
                'password' => $data['password'],
                'sex' => $data['sex'],
                'age' => $data['age'],
                'phone' => $data['phone'],
            ]);
            if($user){
                return response()->json(['info' => 'user_registered'], 200);
            }
            else{
                return response()->json(['error' => 'credentials_exists'], 401);
            }
        }
        catch(\Illuminate\Database\QueryException $ex){
            return response()->json(['error' => 'credentials_exists'], 401);
        }
    }
}
