(ns coffeepot.db)

(def default-db
  {:name "Coffeepot!"
   :firebase-app nil
   :auth-listener false
   :user {:username "User Name"
          :logged-in false}
   :locale :fi
   :current-view :logged-out
   :user-uid nil
   :username nil})
