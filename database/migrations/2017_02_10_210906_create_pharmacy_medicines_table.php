<?php

use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreatePharmacyMedicinesTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('pharmacy_medicines', function (Blueprint $table) {
            $table->increments('id');
            $table->integer('pharmacy_id')->unsigned();
            $table->integer('medicine_id')->unsigned();
            $table->integer('qty');
            $table->date('expiry_date');
            $table->timestamps();
        });

        Schema::table('pharmacy_medicines', function($table) {
            $table->foreign('pharmacy_id')->references('id')->on('pharmacies');
            $table->foreign('medicine_id')->references('id')->on('medicines');
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::drop('pharmacy_medicines');
    }
}
