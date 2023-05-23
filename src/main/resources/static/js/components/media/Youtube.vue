<template>
        <iframe
                  width="560"
                  height="315"
                  :src="targetSrc"
                  frameborder="0"
                  allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share"
                  allowfullscreen
         ></iframe>
</template>


<script>
export default {
    name: 'YouTube',
    props: ['src'],
    computed: {
        targetSrc(){
        let parts = this.src.split('/')
        let link = parts[parts.length-1]
           return `https://www.youtube.com/embed/${link}`
        }
    }
}
</script>


<style scoped>
</style>