package controllers;

import application.Main;

import java.sql.Connection;

public abstract class Controller {

	public abstract void setMain(Main main);

	public abstract void fill();
}
