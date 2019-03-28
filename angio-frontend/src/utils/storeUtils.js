export default {
  addNamespace(types, namespace) {
    return Object.keys(types).reduce(function(result, key) {
      result[key] = `${namespace}/${types[key]}`;
      return result;
    }, {});
  }
};
