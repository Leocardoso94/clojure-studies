(ns giggin.components.orders
  (:require [giggin.state :as state]
            [giggin.helpers :refer [format-price]]))

(defn get-gig-property-by-id [id]
  (fn [property] (get-in @state/gigs [id property])))

(defn order-item [id quant remove-from-order]
  (let [get-gig-property (get-gig-property-by-id id)]
    [:div.item {:key id}
     [:div.img
      [:img {:src (get-gig-property :img)
             :alt (get-gig-property :id)}]]
     [:div.content
      [:p.title (str (get-gig-property :title) " \u00d7 " quant)]]
     [:div.action
      [:div.price (* (get-gig-property :price) quant)]
      [:button.btn.btn--link.tooltip {:data-tooltip "Remove"
                                      :on-click #(remove-from-order id)}
       [:i.icon.icon--cross]]]]))

(defn total
  []
  (->> @state/orders
       (map (fn [[id quant]] (* quant (get-in @state/gigs [id :price]))))
       (reduce +)))


(defn orders []
  (let [remove-from-order #(swap! state/orders dissoc %)
        remove-all-orders #(reset! state/orders {})]
    [:aside
     (if (empty? @state/orders)
       [:div.empty
        [:div.title "You don't have any orders"]
        [:div.subtitle "Click on + to add an order"]]
       [:div.order
        [:div.body
         (for [[id quant] @state/orders]
           (order-item id quant remove-from-order))]
        [:div.total
         [:hr]
         [:div.item
          [:div.content "Total"]
          [:div.action
           [:div.price (format-price (total))]]
          [:button.btn.btn--link.tooltip
           {:data-tooltip "Remove All"
            :on-click #(remove-all-orders)}
           [:i.icon.icon--delete]]]]])]))