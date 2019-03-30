module.exports = {
    outputDir: "dist",

    devServer: {
        port: 8081,
        proxy: "http://localhost:8080",
    },

    pluginOptions: {
        i18n: {
            locale: "ru",
            fallbackLocale: "en",
            localeDir: "locales",
            enableInSFC: true,
        },
    },
};
