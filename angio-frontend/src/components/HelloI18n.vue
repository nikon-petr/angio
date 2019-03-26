<template>
  <p>{{ $t('message', { name: 'Петя' }) }}</p>
</template>

<script>
export default {
  name: 'HelloI18n'
}
</script>

<i18n>
{
  "ru": {
    "hello": "Hello i18n in SFC!"
  }
}
</i18n>
