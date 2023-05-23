import Vue from 'vue'
import Vuex from 'vuex'
import productsApi from 'api/products'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
        profile: frontendData.profile,
        products: frontendData.products

  },
  getters: {
    sortedProducts: state => (state.products || []).sort((a,b) => - (a.id - b.id))
  },
  mutations: {
      addProductMutation(state, product) {
             state.products = [
                ...state.products,
                product
             ]
      },
      updateProductMutation(state, product) {
      const updateIndex = state.products.findIndex(item => item.id === product.id)
      if (updateIndex){
           state.products = [
                ...state.products.slice(0, updateIndex),
                product,
                ...state.products.slice(updateIndex +1)
            ]
        }
      },
      removeProductMutation(state, product) {
      const deletionIndex = state.products.findIndex(item => item.id === product.id)
      if (deletionIndex > -1) {
        state.products = [
            ...state.products.slice(0, deletionIndex),
            ...state.products.slice(deletionIndex + 1)
            ]
        }
      },

  },
  actions: {
       async addProductAction({commit, state}, product){
           const result = await productsApi.add(product)
           const data = await result.json()
           const index = state.products.findIndex(item => item.id === data.id)
           if(index > -1){
            commit('updateProductMutation', data)
           }else{
             commit('addProductMutation', data)
                }

        },
         async updateProductAction({commit}, product){
            const result = await productsApi.update(product)
            const data = await result.json()
            commit('updateProductMutation', data)

        },
         async removeProductAction({commit}, product){
            const result = await productsApi.remove(product.id)
            if (result.ok){
             commit('removeProductMutation', product)
             }
             }

  }
})
