<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;

use App\Http\Requests;
use Tymon\JWTAuth\Facades\JWTAuth;

use App\User;
use App\Medicine;
use App\UserMedicine;
use App\Schedule;

class ApiActivityController extends Controller
{

	public function getallmedicine(){
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
        $data = Medicine::get();
        return response()->json(['info'=>$data]);
	}

	public function getmymedicine(){
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
        $data = UserMedicine::where('user_id',$user->id)->join('medicines','medicines.id','=','user_medicines.medicine_id')->get(['user_medicines.id','user_medicines.qty','user_medicines.medicine_id','medicines.name','medicines.description','medicines.image','medicines.manufacturer','user_medicines.expiry_date']);
        if($data){
        	return response()->json(['info'=>$data]);	
        }
        else{
        	return response()->json(['info'=>'empty']);
        }
		
	}

    public function addmedicine(Request $request){
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
        $data = $request->only('medicine_id','quantity','expiry');
        if(empty($data['medicine_id']) || empty($data['quantity']) || empty($data['expiry'])){
            return response()->json(['error'=>'parameters missing']);
        }
        $temp = \DB::table('user_medicines')->insert([
                'user_id' => $user->id,
                'medicine_id' => $data['medicine_id'],
                'expiry_date' => $data['expiry'],
                'quantity' => $data['quantity'],
            ]);
        if($temp){
        	return response()->json(['info' => 'medicine_added'], 200);
        }
        else{
        	return response()->json(['error' => 'cannot_add'], 401);
        }
    }

    public function removemedicine(Request $request){
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
        $data = $request->only('user_medicine_id');
        if(empty($data['user_medicine_id'])){
            return response()->json(['error'=>'parameters missing']);
        }
        $temp = UserMedicine::where('id',$data['user_medicine_id'])->delete();
        if($temp){
        	return response()->json(['info' => 'medicine_removed'], 200);
        }
        else{
        	return response()->json(['error' => 'cannot_remove'], 401);
        }
    }

    public function getschedule(Request $request){
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
        $data = Schedule::where('user_id',$user->id)->join('medicines','medicines.id','=','schedules.medicine_id')->get(['schedules.id','schedules.medicine_id','medicines.name','medicines.description','medicines.manufacturer','medicines.image','schedules.dosage','schedules.time','schedules.day']);
        if($data){
        	return response()->json(['info'=>$data]);	
        }
        else{
        	return response()->json(['info'=>'empty']);
        }

    }

    public function addschedule(Request $request){
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
        $data = $request->only('medicine_id','dosage','time','day');
        if(empty($data['medicine_id']) || empty($data['dosage']) || empty($data['time']) || empty($data['day'])){
            return response()->json(['error'=>'parameters missing']);
        }
        $temp = \DB::table('schedules')->insert([
                'user_id' => $user->id,
                'medicine_id' => $data['medicine_id'],
                'dosage' => $data['dosage'],
                'time' => $data['time'],
                'day' => $data['day'],
            ]);
        if($temp){
        	return response()->json(['info' => 'schedule_created'], 200);
        }
        else{
        	return response()->json(['error' => 'cannot_create_schedule'], 401);
        }
    }

    public function removeschedule(Request $request){
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
        $data = $request->only('schedules_id');
        if(empty($data['schedules_id'])){
        	return response()->json(['error' => 'parameters missing']);
        }
        $temp = Schedule::where('id',$data['schedules_id'])->delete();
        if($temp){
        	return response()->json(['info' => 'schedule_deleted'], 200);
        }
        else{
        	return response()->json(['error' => 'cannot_delete_schedule'], 401);
        }

    }
}
