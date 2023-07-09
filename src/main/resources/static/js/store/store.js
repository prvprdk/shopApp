import Vue from 'vue'
import Vuex from 'vuex'
import productsApi from 'api/products'
import commentApi from 'api/comment'


Vue.use(Vuex)

export default new Vuex.Store({
  state: {
        profile,
        products,
         ...frontendData,
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
       addCommentMutation(state, comment) {
            const updateIndex = state.products.findIndex(item => item.id === comment.product.id)
            const product = state.products[updateIndex]

            if (!product.comments.find(it => it.id === comment.id )){
                 state.products = [
                      ...state.products.slice(0, updateIndex),
                      {
                        ...product,
                        comments: [
                            ...product.comments,
                            comment
                        ]
                      },
                      ...state.products.slice(updateIndex +1)
                  ]
              }
            },
       addProductPageMutation(state, products){
        const targetProducts = state.products
        .concat(products)
        .reduce((res, val) => {
        res[val.id] = val
        return res
            }, {})
              state.products = Object.values(targetProducts)
       },
       updateTotalPagesMutation (state, totalPages){
        state.totalPages = totalPages
       },
       updateCurrentPageMutation (state, currentPage){
        state.currentPage = currentPage
       }
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
         },
         async addCommentAction ({commit,state}, comment){
             const response = await commentApi.add(comment)
             const data = await response.json()
             commit('addCommentMutation', data)
         },
         async loadPageAction ({commit, state}){
            const response = await productsApi.page(state.currentPage + 1)
            const data = await response.json()
             commit('addProductPageMutation', data.products)
              commit('updateTotalPagesMutation', data.products)
               commit('updateCurrentPageMutation', Math.min(data.currentPage, data.totalPages))
         }
  }
})
