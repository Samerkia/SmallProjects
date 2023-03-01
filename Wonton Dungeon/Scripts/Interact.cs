using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Interact : MonoBehaviour
{
    protected Inventory inventory;
    // Start is called before the first frame update
    protected virtual void Start()
    {
        GameObject inventoryObj = GameObject.FindGameObjectWithTag("Inventory");
        if (inventoryObj != null)
        {
            inventory = inventoryObj.GetComponent<Inventory>();
        }
    }

    // Update is called once per frame
    public virtual void OnInteract()
    {
        Debug.LogWarning("Vir OnInteract called");
    }
}
