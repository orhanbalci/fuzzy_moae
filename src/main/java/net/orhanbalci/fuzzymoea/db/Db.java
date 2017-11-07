package net.orhanbalci.fuzzymoea.db;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import net.orhanbalci.fuzzymoea.entity.*;

public class Db {
  private static final String databaseUrl = "";
  private ConnectionSource connectionSource;
  private Dao<Constraint, Integer> constraintDao;
  private Dao<FoodGroup, Integer> foodGroupDao;
  private Dao<FoodNutrient, Integer> foodNutrientDao;
  private Dao<Food, Integer> foodDao;
  private Dao<NutrientGroup, Integer> nutrientGroupDao;
  private Dao<Nutrient, Integer> nutrientDao;
  private Dao<Unit, Integer> unitDao;

  public Db() {
    try {
      connectionSource = new JdbcConnectionSource(databaseUrl);
      createDao();
    } catch (SQLException exc) {
      System.out.println(exc);
    }
  }

  private void createDao() throws SQLException {
    constraintDao = DaoManager.createDao(connectionSource, Constraint.class);
    foodGroupDao = DaoManager.createDao(connectionSource, FoodGroup.class);
    foodNutrientDao = DaoManager.createDao(connectionSource, FoodNutrient.class);
    foodDao = DaoManager.createDao(connectionSource, Food.class);
    nutrientGroupDao = DaoManager.createDao(connectionSource, NutrientGroup.class);
    nutrientDao = DaoManager.createDao(connectionSource, Nutrient.class);
    unitDao = DaoManager.createDao(connectionSource, Unit.class);
  }

  public void closeConnection() {
    if (null != connectionSource) {
      try {
        connectionSource.close();
      } catch (IOException exc) {
        System.out.println(exc);
      }
    }
  }

  public List<Constraint> getConstraints() {
    try {
      return constraintDao.queryForAll();
    } catch (SQLException exc) {
      System.out.println(exc);
      return Collections.<Constraint>emptyList();
    }
  }
}
