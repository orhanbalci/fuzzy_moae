package net.orhanbalci.fuzzymoea.db;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.orhanbalci.fuzzymoea.entity.*;

public class Db {
  private static final String databaseUrl = "jdbc:mysql://172.17.0.2:3306/DIET";
  private ConnectionSource connectionSource;
  private Dao<Constraint, Integer> constraintDao;
  private Dao<FoodGroup, Integer> foodGroupDao;
  private Dao<FoodNutrient, Integer> foodNutrientDao;
  private Dao<Food, Integer> foodDao;
  private Dao<NutrientGroup, Integer> nutrientGroupDao;
  private Dao<Nutrient, Integer> nutrientDao;
  private Dao<Unit, Integer> unitDao;

  private Map<Integer, Food> foodCache = new HashMap<Integer, Food>();

  public Db() {
    try {
      connectionSource = new JdbcConnectionSource(databaseUrl, "root", "root");
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

  public List<FoodGroup> getFoodGroups() {
    try {
      return foodGroupDao.queryForAll();
    } catch (SQLException exc) {
      exc.printStackTrace();
      return Collections.<FoodGroup>emptyList();
    }
  }

  public List<FoodNutrient> getFoodNutrients() {
    try {
      List<FoodNutrient> result = foodNutrientDao.queryForAll();
      for (FoodNutrient fn : result) {
        foodDao.refresh(fn.getFood());
        nutrientDao.refresh(fn.getNutrient());
      }
      return result;
    } catch (SQLException exc) {
      exc.printStackTrace();
      return Collections.<FoodNutrient>emptyList();
    }
  }

  public List<Food> getFoods() {
    try {
      foodCache.clear();
      List<Food> result = foodDao.queryForAll();
      for (Food f : result) {
        foodGroupDao.refresh(f.getFoodGroup());
        foodCache.put(f.getId(), f);
      }
      return result;
    } catch (SQLException exc) {
      exc.printStackTrace();
      return Collections.<Food>emptyList();
    }
  }

  public Food getFood(int id) {
    if (foodCache.isEmpty()) {
      getFoods();
    }
    return foodCache.get(id);
  }

  public List<NutrientGroup> getNutrientGroups() {
    try {
      return nutrientGroupDao.queryForAll();
    } catch (SQLException exc) {
      exc.printStackTrace();
      return Collections.<NutrientGroup>emptyList();
    }
  }

  public List<Unit> getUnits() {
    try {
      return unitDao.queryForAll();
    } catch (SQLException exc) {
      exc.printStackTrace();
      return Collections.<Unit>emptyList();
    }
  }

  public List<Nutrient> getNutrients() {
    try {
      List<Nutrient> result = nutrientDao.queryForAll();
      for (Nutrient nutrient : result) {
        nutrientGroupDao.refresh(nutrient.getNutrientGroup());
        unitDao.refresh(nutrient.getUnit());
      }
      return result;
    } catch (SQLException exc) {
      exc.printStackTrace();
      return Collections.<Nutrient>emptyList();
    }
  }
}
