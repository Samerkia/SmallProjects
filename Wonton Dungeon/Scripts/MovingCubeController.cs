using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class MovingCubeController : MonoBehaviour
{
    // Start is called before the first frame update
    [SerializeField]
    private float speed = 0.1f;

    private bool enableMovement = false;
    private Coroutine movementCoroutine = null;
    void Start()
    {
        movementCoroutine = StartCoroutine(Move());
    }

    // Update is called once per frame
    void Update()
    {

    }

    public void ToggleMovement()
    {
        enableMovement = (!enableMovement);

        if (movementCoroutine != null)
        {
            StopCoroutine(movementCoroutine);
            movementCoroutine = null;
        }
        if (enableMovement)
        {
            StartCoroutine(Move());
        }
        //else
        //{
        //    StopCoroutine(Move());
        //}

    }
    IEnumerator Move()
    {
        while (true)
        {
            Vector3 pos = transform.position;
            pos.x += speed;
            transform.position = pos;

            yield return null;
        }
    }
}
